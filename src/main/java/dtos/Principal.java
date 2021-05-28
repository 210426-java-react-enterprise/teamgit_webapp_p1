package dtos;

import io.jsonwebtoken.*;
import models.*;

public class Principal {
    private int id;
    private String username;


    /*
    New users will all get the default BASIC_USER role
    Make a protected endpoint that only admins can change peoples roles
    Make yourself an admin
     */
    private AppUser.Role role;

    public Principal(Claims jwtClaims){
        this.id = Integer.parseInt(jwtClaims.getId());
        this.username = jwtClaims.getSubject();
        this.role = AppUser.Role.valueOf(jwtClaims.get("role", String.class));
    }

}
