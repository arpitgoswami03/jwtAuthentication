package com.security.jwtauthentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
@Service
public class JWTService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    private HashMap<Claims, Claims> claims = new HashMap<>();

    public String generateToken(String username, String password) {
        System.out.println("generateToken");
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()*(60+10+10)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        return null;
    }

    public String extractClaim(String token, String name) {
        return null;
    }



}
