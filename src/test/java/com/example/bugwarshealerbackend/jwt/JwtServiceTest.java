package com.example.bugwarshealerbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    @Test
    public void test() {
        assertTrue(true);
    }

    public static String ISSUER = "com.example.bugwarsbackend";
    @Test
    public void createToken_shouldGenerateValidToken() {
        String oldSecret = JwtService.SECRET_KEY;
        try {
            String secret = "a2ltbHluXyZeKigmKGFuZComJSYqP3lhZ211cjIwMjQ=";
            JwtService.SECRET_KEY = secret;


            String username = "testUser";
            String token = JwtService.createToken(username);


            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(JwtService.getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            assertEquals(username, claimsJws.getBody().getSubject());
            assertEquals(ISSUER, claimsJws.getBody().getIssuer());

        }
        finally {
            JwtService.SECRET_KEY = oldSecret;
        }
    }

}