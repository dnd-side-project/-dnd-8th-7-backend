package com.dnd8th.auth.jwt;

import com.dnd8th.error.ErrorResponse;
import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.auth.AccessTokenInvalidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProviderService jwtProviderService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = jwtProviderService.getJwtTokenFromRequest(request);
            if (StringUtils.hasText(jwtToken) && jwtProviderService.validateToken(jwtToken)) {
                Authentication authentication = jwtProviderService.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException expiredJwtException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (AccessTokenInvalidException accessTokenInvalidException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_INVALID);
        } catch (JwtException jwtException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ErrorCode.ACCESS_TOKEN_INVALID);
        }
        filterChain.doFilter(request, response);
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response,
            ErrorCode errorCode) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                ErrorResponse.from(errorCode)
        ));
    }
}
