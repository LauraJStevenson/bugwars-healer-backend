package com.example.bugwarshealerbackend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;

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

    public static String getUserNameForToken(String token)
    {
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
