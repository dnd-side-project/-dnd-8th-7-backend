package com.dnd8th.auth.jwt;

import com.dnd8th.entity.Role;
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
    private final long accessTokenPeriod = 1000L * 60L * 60L; // 60분
    private final long refreshPeriod = 1000L * 60L * 60L * 24L * 14L;      // 14일
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

    //추후 수정
    public String generateTokenFromRefreshToken(String refreshToken) {
        return null;
    }

    public String getEmailFromHeaderAccessToken(HttpServletRequest request) {
        String jwtToken = getJwtTokenFromRequest(request);
        String email = getEmail(jwtToken);

        return email;
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

    public String getAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();

            return (String) claims.get("access_token");
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
//        String accessToken = getAccessToken(token);

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
