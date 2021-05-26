package services;


import io.jsonwebtoken.*;
import models.*;

import javax.crypto.spec.*;
import javax.servlet.http.*;
import javax.xml.bind.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class JWTService {

    private static String SECRET_KEY;
    static {
        try {
            Properties props = new Properties();
            InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("jwt.properties");
            props.load(is);

            SECRET_KEY = props.getProperty("SECRET_KEY");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createJWT(AppUser user){
        //The JWT signature hashing algorithm used to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //gets the current date of token issuance
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Signing the JWT with the secret key
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        //Setting JWT claims
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(now)
                .setId(Integer.toString(user.getId()))
                .setSubject(user.getUsername())
                .setIssuer("teamgit")
                //.claim("role", "admin")  //implement when i figure out how to do roles
                .setExpiration(new Date(nowMillis + 1000000)) //sets the expiration date to 14 mins
                .signWith(signatureAlgorithm, signingKey);

        return jwtBuilder.compact();

    }

    public void checkToken(HttpServletRequest req){
        //Get HTTP header named "Authorization"
        String header = req.getHeader("Authorization");

        //Validate header values and chec the prefix
        if(header==null || !header.startsWith("Bearer")){
            //Request originates from an unauthenticated origin
            return;
        }

        //Get the token
        String token = header.replaceAll("Bearer", "");

        try{
            //Validate the token
            Claims claims = decodeJWT(token);

            //Obtain the principal/subject stored in the JWT
//            Principal principal = new Principal(
//                    Integer.parseInt(claims.getId()),
//                    claims.getSubject(),
//                    claims.getExpiration());

//            req.setAttribute("principal", principal);

        }catch(Exception e){
            e.printStackTrace();
        }



    }


    public static Claims decodeJWT(String jwtoken) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwtoken)
                .getBody();

        return claims;
    }


}
