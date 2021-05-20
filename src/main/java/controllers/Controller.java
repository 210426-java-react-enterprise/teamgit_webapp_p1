package controllers;



import models.*;
import repos.*;
import services.*;

import javax.servlet.http.*;
import java.io.*;


public class Controller {

    UserRepo userRepo = new UserRepo();
    UserService userService = new UserService();

    //TODO implement register
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //Acquire parameters
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username"); //allows access to the parameters of the body
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String dob = req.getParameter("dob");

        // 2. construct an AppUser with that information
        AppUser user = new AppUser(username, password, email, firstName, lastName, dob);
        System.out.println(user);

        //regex to test input validity
        String invalidUserMessage = userService.isUserValid(user);
        System.out.println("invalidUserMessage: " + invalidUserMessage);


        if(invalidUserMessage == ""
                && userService.authenticateUniqueCredentials(username, email, userRepo)){
            // 3. save the user in the db with the register method from the service
            //TODO might want it to return an AppUser instead of void
             //AppUser idUser = userRepo.save(user);
            userRepo.save(user);
        }
        else{
            //if user is invalid, it'll send a response with a message of what went wrong
            resp.setStatus(400);
            resp.getWriter().println("We found an error with your registration: \n"
                   + invalidUserMessage);
       }

    }

    //TODO implement authenticate
    public void authenticate(HttpServletRequest req, HttpServletResponse resp) {
        String username = "";
        String password = "";
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        userService.authenticateUserCredentials(user, userRepo);
    }

    //TODO implement validateDeposit
    public void validateDeposit(HttpServletRequest req, HttpServletResponse resp) {
        double deposit_am = 0.0;
        userService.depositVerify(deposit_am, userRepo);
    }

    //TODO implement validateWithdrawal
    public void validateWithdrawal(HttpServletRequest req, HttpServletResponse resp) {
        double withdraw_am = 0.0;
        userService.withdrawVerify(withdraw_am, userRepo);
    }

    //public void isUsername

}
