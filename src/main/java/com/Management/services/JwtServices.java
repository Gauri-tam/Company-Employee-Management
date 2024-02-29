package com.Management.services;

import com.Management.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServices {

    @Value("${application.security.jwt.security-key}")
    private String secreteKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpired;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpired;

    // extract token

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> resolveClaims){
        final Claims claims = extractAllClaims(token);
        return resolveClaims.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] byteKey = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(byteKey);
    }

    // generate token

    public String generateAccessToken(User user){
        return buildTokens(new HashMap<>(), user, jwtExpired );
    }


    public String generateRefreshToken(User user) {
        return buildTokens(new HashMap<>(), user, refreshExpired );
    }

    private String buildTokens(Map<String , Object> claims, User user, long extraction){
        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("typ", "JWT")
                .setSubject(user.getEmail())
                .claim("name", user.getFirstName()+" "+user.getLastName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + extraction))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // validate the token

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isExpiredToken(token);
    }

    private boolean isExpiredToken(String token) {
        return expiredExtract(token).before(new Date());
    }

    private Date expiredExtract(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
