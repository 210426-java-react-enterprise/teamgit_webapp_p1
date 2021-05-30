package security;

import dtos.*;
import io.jsonwebtoken.*;
import jdk.nashorn.internal.parser.*;
import models.*;

import javax.servlet.http.*;
import java.util.*;

public class JwtService {


    private JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public JwtService() {

    }

    //You need to manually take tokens, copying it with its prefix, over to the Headers of your next request
    //Type in your HEADER in the key, and then paste your key into the VALUE and it should work
    public String createJwt(AppUser subject){

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
                .setExpiration(new Date(nowMillis + jwtConfig.getEXPIRATION())) //sets the expiration date to 14 mins
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return jwtConfig.getPREFIX() + jwtBuilder.compact();
    }

    public void parseToken(HttpServletRequest req){
        try{
            String header = req.getHeader(jwtConfig.getHEADER());

            if(header == null || !header.startsWith(jwtConfig.PREFIX)){
                return;
            }

            String token = header.replaceAll(jwtConfig.getPREFIX(), "");

            //the parser will throw certain exceptions that can potentially happen if given bad or expired JWT!
            //hover over .parseClaimsJwt()
            Claims jwtClaims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                                   //.setSigningKey(jwsConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            req.setAttribute("principal", new Principal(jwtClaims));


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
