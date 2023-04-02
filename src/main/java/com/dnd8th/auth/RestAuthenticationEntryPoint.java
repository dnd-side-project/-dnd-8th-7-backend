package com.dnd8th.auth;

import com.dnd8th.error.ErrorResponse;
import com.dnd8th.error.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        String authorization = request.getHeader("Authorization");

        if (authorization == null || authorization.equals("") || authorization.equals("Bearer ")) {
            response.setStatus(ErrorCode.USER_ACCESS_DENIED.getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(
                    ErrorResponse.from(ErrorCode.USER_ACCESS_DENIED)));
        }
    }
}
