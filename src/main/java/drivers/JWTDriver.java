package drivers;

import io.jsonwebtoken.*;
import models.*;
import services.*;

public class JWTDriver {

    public static void main(String[] args) {
        AppUser user = new AppUser("swekevin", "password", "swekevin@gmail.com", "Kevin", "Chang", "1997-11-09");
        user.setId(25);

        String token = JWTService.createJWT(user);
        System.out.println(token);

        Claims claims = JWTService.decodeJWT(token);
        System.out.println(claims);
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuer());
        System.out.println(claims.getExpiration());
        System.out.println();


    }

}
