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

    private Repo repo;

    public UserController(Repo repo) {
        this.repo = repo;
    }

    //TODO implement register
    public boolean register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        boolean result = false;

        //Acquire parameters
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username"); //allows access to the parameters of the body
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String dob = req.getParameter("dob");

        // 2. construct an AppUser with that information
        AppUser appUser = new AppUser(username, password, email, firstName, lastName, dob);
        //regex to test input validity
        //String invalidUserMessage = userService.isUserValid(user);
        // System.out.println("invalidUserMessage: " + invalidUserMessage);





        //TODO might want it to return an AppUser instead of void
        try {
            repo.create(appUser);
            repo.insert(appUser);
            ArrayList<Object> registeredUser = repo.select(appUser);

            //using Jackson to map the user into a JSON
            writer.write(mapper.writeValueAsString(registeredUser));

            result = true;

        }catch(ResourcePersistenceException e){
            resp.setStatus(400);
            writer.write("Could not create a table to hold your information!");
        }
        catch(ResourceDuplicationException e){
            resp.setStatus(400);
            writer.write("Your username or email is already taken!");
        }catch(Exception e){
            resp.setStatus(500);
        }

        return result;


        }



    //TODO implement authenticate
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

            ArrayList<Object> returnCredentials = repo.select(user);
            writer.write(mapper.writeValueAsString(returnCredentials));

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
