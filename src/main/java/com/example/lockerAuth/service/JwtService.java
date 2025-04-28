package com.example.lockerAuth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String getToken(UserDetails newUser) {
        return getToken(new HashMap<>(), newUser);
    }

    private String getToken(Map<String, Object> claims, UserDetails newUser) {
        return Jwts
            .builder()
            .claims(claims)
            .subject(newUser.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+(1000*60*60)))
            .signWith(getKey())
            .compact();
        }

    private SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !!expired(token));
    }

    private Claims getAllClaims(String tkn) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(tkn)
                .getPayload();
    }

    public <T> T getClaim(String tkn, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(tkn);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String tkn) {
        return getClaim(tkn, Claims::getExpiration);
    }

    private boolean expired(String tkn) {
        return getExpiration(tkn).before(new Date());
    }

}
