package controllers;



import com.fasterxml.jackson.databind.*;
import dtos.*;
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
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        //Acquire parameters
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username"); //allows access to the parameters of the body
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String dob = req.getParameter("dob");

        // 2. construct an AppUser with that information
        AppUser appUser = new AppUser(username, password, email, firstName, lastName, dob);
        UserInformation user = new UserInformation(username, password, email, firstName, lastName, dob);
        System.out.println(user);

        //regex to test input validity
        //String invalidUserMessage = userService.isUserValid(user);
        // System.out.println("invalidUserMessage: " + invalidUserMessage);



        //TODO might want it to return an AppUser instead of void
        repo.create(appUser);
        repo.insert(user);
        //AppUser registeredUser = (AppUser) repo.select(user).get(0);

        //using Jackson to map the user into a JSON
        //writer.write(mapper.writeValueAsString(registeredUser));
        writer.write("User was successfully registered!");

        }



    //TODO implement authenticate
    public boolean authenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);

        ArrayList<Object> returnList = repo.select(user);
        StringBuilder authOutput = new StringBuilder();

        for (Object o : returnList){//should only be size==1, but just in case later implementations change it
            authOutput.append(o);
            //user = (AppUser) o;//set stats for returned user, just in case this method is to evolve utilizing this
        }
        //if nothing returned, then authentication failed

        if(authOutput.length() > 0) {
            writer.write(authOutput.toString());
            return true;
        }else{
            writer.write("Authentication failed!");
            return false;
        }

    }

}