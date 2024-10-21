package com.example.springSecurityExample.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "8+vXK/1n3SjPwuPPOabbTgWnqny5Z8BuZLkk7Llr0Dz8vzydeaCflDZWb2ymSGFabpKRcMd2jyFeyjikY8YU+RNOs6xNUjhq1QBufLpgd0YexGi9Cc+Va+T+lpHBm6ZfEYuyG+5Zisu8J3Vn1DQzMsXpivsnX2dmNjBBcx8xKCmfTJmJx6j8SzeEOFVmj+ypISPsTO7tVJ6i8VORChncbZZC6lD9/Jbh8QYLY/3cwlcDgWEBOzL3oVmlFVW5QqScm8x2IU+9cv0PudkFSg5Yh+UEOY6E14o41aNf0MWuB/7vCrDaHAVQEt65yiwruSSYvq+q3yCMlTBAmMQkUA4dEg2ldmKIW9qr6D05qm2Lsag=";

    public String generateToken(UserDetails utente) {
        return generateToken(new HashMap<>(), utente);
    }

    public String generateToken(Map<String, Object> claims, UserDetails utente) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(utente.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000000L *6000*24))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

}
