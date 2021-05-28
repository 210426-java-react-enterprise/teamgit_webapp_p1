package controllers;



import com.fasterxml.jackson.databind.*;
import dtos.*;
import exceptions.*;
import jdk.nashorn.internal.runtime.arrays.*;
import models.*;
import repos.*;
import services.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {

            //Acquire user information using a DTO
            UserInformation userInfo = mapper.readValue(req.getInputStream(), UserInformation.class);

            // 2. construct an AppUser with the mapped DTO
            AppUser appUser = new AppUser(
                    userInfo.getUsername(),
                    userInfo.getPassword(),
                    userInfo.getEmail(),
                    userInfo.getFirstName(),
                    userInfo.getLastName(),
                    userInfo.getDob());

            ArrayList<Object> registeredUser = userService.verifyRegistration(appUser);

            //using Jackson to map the user into a JSON
            writer.write(mapper.writeValueAsString(registeredUser));

        }catch(ResourceInvalidException | UsernameUnavailableException | EmailUnavailableException e){
            resp.setStatus(400);
            writer.write(e.getMessage());
        }catch(Exception e){
            resp.setStatus(500);
            e.printStackTrace();
        }
    }



    //TODO implement authenticate return void
    public boolean authenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            AppUser user = new AppUser();
            user.setUsername(username);
            user.setPassword(password);

//            ArrayList<Object> returnCredentials = repo.select(user);
//            writer.write(mapper.writeValueAsString(returnCredentials));

        }catch(ResourceNotFoundException e){
            resp.setStatus(401);
            writer.write("Authentication failed!");
        }
        catch(Exception e){
            resp.setStatus(500);
        }
        return result;
    }

}