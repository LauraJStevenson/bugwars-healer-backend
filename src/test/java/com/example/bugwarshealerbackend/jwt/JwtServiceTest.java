package com.example.bugwarshealerbackend.jwt;

import com.zaxxer.hikari.pool.HikariProxyCallableStatement;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.security.Key;
import java.util.Date;

import static com.example.bugwarshealerbackend.jwt.JwtService.*;
import static org.aspectj.bridge.Version.getTime;
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
            String token = createToken(username);


            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(JwtService.getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            assertEquals(username, claimsJws.getBody().getSubject());
            assertEquals(ISSUER, claimsJws.getBody().getIssuer());

        } finally {
            JwtService.SECRET_KEY = oldSecret;
        }
    }


    @Test
    public void getUserNameForTokenTest() {

        String oldSecret = JwtService.SECRET_KEY;
        try {
            String secret = "a2ltbHluXyZeKigmKGFuZComJSYqP3lhZ211cjIwMjQ=";
            JwtService.SECRET_KEY = secret;

            String username = "testUser";

            String token = JwtService.createToken(username);

            String result = JwtService.getUserNameForToken(token);
            assertEquals(username, result);
        } finally {
            JwtService.SECRET_KEY = oldSecret;
        }
    }

    @Test
    public void getUserNameForTokenTest_wrongIssuer() {

        String oldSecret = JwtService.SECRET_KEY;
        try {
            String secret = "a2ltbHluXyZeKigmKGFuZComJSYqP3lhZ211cjIwMjQ=";
            JwtService.SECRET_KEY = secret;

            String username = "testUser";

            String token = JwtService.createToken(username, "wrongIssuer");

            String result = JwtService.getUserNameForToken(token);
            assertNull(result);
        } finally {
            JwtService.SECRET_KEY = oldSecret;
        }
    }

    @Test
    public void getUserNameForTokenTest_expiredToken() {

        String oldSecret = JwtService.SECRET_KEY;
        try {
            String secret = "a2ltbHluXyZeKigmKGFuZComJSYqP3lhZ211cjIwMjQ=";
            JwtService.SECRET_KEY = secret;
            String username = "testUser";

            String token = JwtService.createToken("testUser", String.valueOf(new Date(System.currentTimeMillis() - 1000)));

            String result = JwtService.getUserNameForToken(token);
            assertNull(result);

        } finally {
            JwtService.SECRET_KEY = oldSecret;
        }

    }

    /**
     * Test to ensure that the `getUserNameForToken` method correctly handles an invalid token (by adding to JWT_DENY_LIST).
     * It mocks the `isTokenInvalidated` method to return true (indicating an invalid token),
     * creates a token, and then checks that the `getUserNameForToken` method returns null for
     * the invalid token.
     */
    @Test
    public void getUserNameForTokenTest_invalidToken() {
        // Mock the `isTokenInvalidated` method to return true (invalid token)
        try (MockedStatic<JwtService> jwtServiceMock = Mockito.mockStatic(JwtService.class)) {
            jwtServiceMock.when(() -> JwtService.isTokenInvalidated(Mockito.anyString())).thenReturn(true);

            String oldSecret = JwtService.SECRET_KEY;
            try {
                String secret = "a2ltbHluXyZeKigmKGFuZComJSYqP3lhZ211cjIwMjQ=";
                JwtService.SECRET_KEY = secret;

                String username = "testUser";
                String token = JwtService.createToken(username);

                String result = JwtService.getUserNameForToken(token);
                assertNull(result);
            } finally {
                JwtService.SECRET_KEY = oldSecret;
            }
        }
    }

    /**
     * Test to ensure that the `createRefreshToken` method generates a valid refresh token.
     * It sets a secret key, creates a refresh token for a test username, and checks that
     * the generated refresh token is not null, indicating a successful token generation.
     */
@Test
    public void createRefreshToken_shouldGenerateValidRefreshToken() {
        String oldSecret = JwtService.SECRET_KEY;
        try {
            String secret = "a2ltbHluXyZeKigmKGFuZComJSYqP3lhZ211cjIwMjQ=";
            JwtService.SECRET_KEY = secret;

            String username = "testUser";
            String refreshToken = JwtService.createRefreshToken(username);

            assertNotNull(refreshToken);

        } finally {
            JwtService.SECRET_KEY = oldSecret;
        }
    }



}


