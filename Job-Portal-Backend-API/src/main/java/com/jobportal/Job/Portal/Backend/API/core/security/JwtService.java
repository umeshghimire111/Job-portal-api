package com.jobportal.Job.Portal.Backend.API.core.security;

import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.jobportal.Job.Portal.Backend.API.repository.UserTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final UserTokenRepository userTokenRepository;

    public JwtService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getName());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().getName());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 2))
                .and()
                .signWith(getKey())
                .compact();
    }

    public void setHttpOnlyCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(maxAge);
        cookie.setDomain("");
        response.addCookie(cookie);
    }

    public void clearCookie(String cookieName, HttpServletResponse response) {
        Cookie clearedCookie = new Cookie(cookieName, null);
        clearedCookie.setPath("/");
        clearedCookie.setHttpOnly(true);
        clearedCookie.setSecure(true);
        clearedCookie.setMaxAge(0);
        response.addCookie(clearedCookie);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateAccessToken(String token, UserDetails userDetails) {
        boolean isTokenValid = userTokenRepository.existsByAccessTokenAndLoggedOutFalse(token);
        final String email = extractEmail(token);
        return(email.equals(userDetails.getUsername()) && !isTokenExpired(token)) && isTokenValid;
    }

    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        boolean isTokenValid = userTokenRepository.existsByRefreshTokenAndLoggedOutFalse(token);
        final String email = extractEmail(token);
        return(email.equals(userDetails.getUsername()) && !isTokenExpired(token)) && isTokenValid;
    }
}
