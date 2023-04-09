package com.dnd8th.auth.jwt;

import com.dnd8th.entity.Role;
import com.dnd8th.error.exception.auth.AccessTokenExpiredException;
import com.dnd8th.error.exception.auth.AccessTokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtProviderService {

    private final HttpSession httpSession;
    private final long accessTokenPeriod = 1000L * 60L * 60L * 24L * 7L; // 7일
    @Value("${app.auth.token-secret-key}")
    private String secretKey;
    private Key key;


    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
    }

    public String generateToken(String email, String accessToken) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", Role.USER.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenPeriod))
                .signWith(key)
                .compact();
    }

    public String refreshToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            String email = claims.getSubject();
            if (claims.getExpiration().before(new Date())) {
                throw new AccessTokenExpiredException();
            }
            Date newExpiration = new Date(System.currentTimeMillis() + accessTokenPeriod);
            return Jwts.builder()
                    .setSubject(email)
                    .claim("access_token", (String) claims.get("access_token"))
                    .claim("role", Role.USER.name())
                    .setIssuedAt(new Date())
                    .setExpiration(newExpiration)
                    .signWith(key)
                    .compact();
        } catch (JwtException | IllegalArgumentException e) {
            throw new AccessTokenInvalidException();
        }
    }

    public String getEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException expiredJwtException) {
            return expiredJwtException.getClaims().getSubject();
        }
    }

    public String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            throw expiredJwtException;
        } catch (JwtException jwtException) {
            throw new AccessTokenInvalidException();
        }
    }

    public Authentication getAuthentication(String token) {
        String email = getEmail(token);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = User.builder()
                .username(email)
                .password("")
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
