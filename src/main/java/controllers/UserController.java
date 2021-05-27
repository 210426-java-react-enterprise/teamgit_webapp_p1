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

            //Acquire parameters
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String username = req.getParameter("username"); //allows access to the parameters of the body
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String dob = req.getParameter("dob");

            // 2. construct an AppUser with that information
            AppUser appUser = new AppUser(username, password, email, firstName, lastName, dob);

            ArrayList<Object> registeredUser = userService.verifyRegistration(appUser);

            //using Jackson to map the user into a JSON
            writer.write(mapper.writeValueAsString(registeredUser));

        }catch(ResourceInvalidException | UsernameUnavailableException | EmailUnavailableException e){
            resp.setStatus(400);
            writer.write(e.getMessage());
        }catch(Exception e){
            resp.setStatus(500);
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
