package com.example.bugwarshealerbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtService {

    public static String ISSUER = "com.example.bugwarsbackend";
    private static String SECRET_KEY = "VGhpcyBpcyB0aGUgc2VjcmV0IGZvciBCdWdXYXJzIHNlcnZlci4=";

    public static String createToken(String username)
    {
        Date now = new Date();

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(username)
                .setIssuer(ISSUER)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static String getUserNameForToken(String token)
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (!claims.getIssuer().equals(JwtService.ISSUER))
        {
            return null;
        }

        return claims.getSubject();
    }

    private static Key getSignInKey()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
