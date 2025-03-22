package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // ✅ Usamos SecretKey en lugar de Key
    private static final SecretKey ACCESS_SECRET_KEY = Jwts.SIG.HS256.key().build(); // Genera una clave para HS256
    private static final SecretKey REFRESH_SECRET_KEY = Jwts.SIG.HS256.key().build(); // Genera una clave para HS256

    private static final long ACCESS_EXPIRATION = 900000; // 15 minutos
    private static final long REFRESH_EXPIRATION = 604800000; // 7 días

    // ✅ Método público para acceder a ACCESS_SECRET_KEY
    public static SecretKey getAccessSecretKey() {
        return ACCESS_SECRET_KEY;
    }

    // ✅ Método público para acceder a REFRESH_SECRET_KEY
    public static SecretKey getRefreshSecretKey() {
        return REFRESH_SECRET_KEY;
    }

    // ✅ Generar access token
    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername(), ACCESS_SECRET_KEY, ACCESS_EXPIRATION);
    }

    // ✅ Generar refresh token
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername(), REFRESH_SECRET_KEY, REFRESH_EXPIRATION);
    }

    // ✅ Método para construir tokens
    private String buildToken(String subject, SecretKey secretKey, long expiration) {
        return Jwts.builder()
                .subject(subject) // ✅ Usamos subject() en lugar de setSubject()
                .issuedAt(new Date()) // ✅ Usamos issuedAt() en lugar de setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + expiration)) // ✅ Usamos expiration() en lugar de setExpiration()
                .signWith(secretKey) // ✅ Usamos signWith() con SecretKey
                .compact();
    }

    // ✅ Validar token
    public boolean validateToken(String token, UserDetails userDetails, SecretKey secretKey) {
        final String username = extractUsername(token, secretKey);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secretKey));
    }

    // ✅ Extraer username del token
    public String extractUsername(String token, SecretKey secretKey) {
        return extractClaim(token, Claims::getSubject, secretKey);
    }

    // ✅ Extraer fecha de expiración del token
    public Date extractExpiration(String token, SecretKey secretKey) {
        return extractClaim(token, Claims::getExpiration, secretKey);
    }

    // ✅ Extraer un claim específico del token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, SecretKey secretKey) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    // ✅ Extraer todos los claims del token
    private Claims extractAllClaims(String token, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey) // ✅ Usamos verifyWith() en lugar de setSigningKey()
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ Verificar si el token ha expirado
    private boolean isTokenExpired(String token, SecretKey secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
    }

    // ✅ Validar refresh token
    public boolean validateRefreshToken(String refreshToken) {
        return !isTokenExpired(refreshToken, REFRESH_SECRET_KEY);
    }

    // ✅ Obtener username desde un refresh token
    public String getUsernameFromRefreshToken(String refreshToken) {
        return extractUsername(refreshToken, REFRESH_SECRET_KEY);
    }
}