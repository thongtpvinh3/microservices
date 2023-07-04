package com.service.apigateway.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public List<String> extractRole(String token) {
        return extractAllClaims(token).get("role", List.class);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JwtConstant.JWT_SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        //Check token has created before time limit
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token) {
        final String user = extractUsername(token);
        List<String> role = extractRole(token);
        return user != null && !role.isEmpty() && !isTokenExpired(token);
    }

    public boolean validateAdminToken(String token) {
        String username = extractUsername(token);
        List<String> roles = extractRole(token);
        return roles.contains("ROLE_ADMIN") && username != null && !isTokenExpired(token);
    }
}
