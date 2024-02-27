package com.example.bugwarshealerbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
@Service
public class JwtService {
    public static String ISSUER = "com.example.bugwarsbackend";
    static String SECRET_KEY = System.getenv("JWT_TOKEN_GENERATED");

    // 1hour is 3600 * 1000 milliseconds.
    public static final int EXPIRY_IN_MILLISECOND = 3600 * 1000;

    //System.getenv("JWT_TOKEN_GENERATED") is the syntax used to access the value of
    //our generated token. You should add the variable JWT_TOKEN_GENERATED to your .env
    //file, which is gitignored by default for security reasons. Please contact Laura or Yagmur
    //to get the values that should be in there.

    private static final HashSet<String> JWT_DENY_LIST = new HashSet<>();

    public static void invalidateToken(String token) {
        JWT_DENY_LIST.add(token);
    }
    public static boolean isTokenInvalidated(String token) {
        return JWT_DENY_LIST.contains(token);
    }

    public static void addToDenyList(String token)
    {
        JWT_DENY_LIST.add(token);
    }

    public static boolean isDenyListed(String token)
    {
        return JWT_DENY_LIST.contains(token);
    }

    public static String createToken(String username)
    {
        return createToken(username, ISSUER);
    }

    static String createToken(String username, String issuer)
    {
        Date now = new Date();

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now) //when the token was issued
                .setSubject(username) //set the subject claim representing the user association with the token
                .setIssuer(issuer) // sets the issuer claim in the JWT indicating the entity that issued the token
                .setExpiration(new Date(now.getTime() + EXPIRY_IN_MILLISECOND)) //specifies when the token will expire
                .signWith(getSignInKey(), SignatureAlgorithm.HS256); /*It signs the JWT with a secret key obtained from the getSignInKey()
                                                                       method using the HMAC SHA-256 algorithm*/

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    /**
     * Creates a refresh token for a given username. This token is used to obtain a new access token once the current access token expires.
     * The refresh token is signed using the HMAC SHA-256 algorithm and includes information such as the token ID, issue date, subject, issuer, and expiration time.
     *
     * @param username The username for whom the refresh token is being created. This is set as the subject in the JWT claims.
     * @return A String representing the JWT refresh token. This token is a compact, URL-safe string serialized from the JWT claims.
     */
    public static String createRefreshToken(String username) {
        Date now = new Date();
        long expiry = 7200000;
        String jti = UUID.randomUUID().toString();

        JwtBuilder builder = Jwts.builder()
                .setId(jti)
                .setIssuedAt(now)
                .setSubject(username)
                .setIssuer(ISSUER)
                .setExpiration(new Date(now.getTime() + expiry))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256);
        return builder.compact();
    }

    public static String getUserNameForToken(String token) {

        if (token == null || isTokenInvalidated(token)) {
            return null; // Token is invalidated
        }

        Claims claims = Jwts.parserBuilder() //uses the jwt class from the jjwt library to parse and validate the JWT
                .setSigningKey(getSignInKey()) //sets the sign in let using the getSignInKey method
                .build()//builds the parser
                .parseClaimsJws(token) //parses the JSON web signature from the provided token
                .getBody(); //getbody retrieves the body (claims) of the JWT

        if (!claims.getIssuer().equals(JwtService.ISSUER)) //checks if the issuer of the JWT is equal to the expected issuer. If not, then it returns null
        {
            return null;
        }

        // check token expiry. If token expired, return null.
        Date expiryDate = claims.getExpiration();
        if (expiryDate.before(new Date()))
        {
            return null;
        }

        return claims.getSubject(); //If the issuer is valid and the token is not expired, the method returns the subject of the JWT that represents the username.
    }

    static Key getSignInKey() {return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));}
}
