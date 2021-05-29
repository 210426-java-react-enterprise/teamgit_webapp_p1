package security;

import dtos.*;
import io.jsonwebtoken.*;
import jdk.nashorn.internal.parser.*;
import models.*;

import javax.servlet.http.*;
import java.util.*;

public class JwtService {


    public JwtService(){

    }

    //You need to manually take tokens, copying it with its prefix, over to the Headers of your next request
    //Type in your HEADER in the key, and then paste your key into the VALUE and it should work
    public static String createJwt(AppUser subject){

        //gets the current date of token issuance
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Setting JWT claims
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(now)
                .setId(Integer.toString(subject.getId()))
                .setSubject(subject.getUsername())
                .setIssuer("teamgit")
                .claim("role", subject.getRole().toString())  //I need an enum in the passed-in subject ROLES
                .setExpiration(new Date(nowMillis + JwtConfig.EXPIRATION)) //sets the expiration date to 14 mins
                .signWith(JwtConfig.getSigAlg(), JwtConfig.getSigningKey());

        return JwtConfig.PREFIX + jwtBuilder.compact();
    }

    public static void parseToken(HttpServletRequest req){
        try{
            String header = req.getHeader(JwtConfig.HEADER);

            if(header == null || !header.startsWith(JwtConfig.PREFIX)){
                return;
            }

            String token = header.replaceAll(JwtConfig.PREFIX, "");

            //the parser will throw certain exceptions that can potentially happen if given bad or expired JWT!
            //hover over .parseClaimsJwt()
            Claims jwtClaims = Jwts.parser()
                    .setSigningKey(JwtConfig.getSigningKey())
                                   //.setSigningKey(jwsConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            req.setAttribute("principal", new Principal(jwtClaims));


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
