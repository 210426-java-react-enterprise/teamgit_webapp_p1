package services;

import models.*;
import repos.*;

import java.util.regex.*;

public class UserService {

    //TODO implementation
    public boolean authenticateUniqueCredentials(String username, String email, UserRepo userRepo){
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        userRepo.isUsernameAvailable(user);
        userRepo.isEmailAvailable(user);
        return false;
    }
    //TODO authenticate username and password
    public boolean authenticateUserCredentials(AppUser user, UserRepo userRepo){

        //AppUser currentUser = (AppUser) userRepo.findUserByUsernameAndPassword(user);

        //if currentUser returns null, then return false
        //if currentUser returns not null, then return true
        //the currentUser can be used to add to this app's session

        return false;
    }

    /**
     * verifies that the deposit is positive, if it is it will invoke the deposit
     * @param deposit_am
     */
    //TODO implement depositVerify
    public void depositVerify(double deposit_am, UserRepo userRepo){
//        if (deposit_am < 0) {
//            System.out.println("Deposit value must be positive!");
//        } else{
//            //AccountsDAO.deposit(getId(), deposit_am);
//        }
    }
    /**
     * verifies that the withdraw is positive and would not leave the user with negative balance,
     * as long as it is it will invoke the withdraw. Does need to call the db to see if it would overdraft
     * but it should still be done before we try to write to it.
     * @param withdraw_am
     */
    //TODO implement withdrawVerify
    public void withdrawVerify(double withdraw_am, UserRepo userRepo){
//        if (withdraw_am < 0 || withdraw_am > AccountRepo.fetchBalance(getId())) {
//            System.out.println("Withdrawal value must be greater than zero and less than account balance!");
//        } else{
//            //AccountsDAO.withdraw(getId(), withdraw_am);
//        }
    }

    public String isUserValid(AppUser user) {

        //boolean check = true;

        if (user == null) return "";
        //Regex expression to check for usernames of length 3-20
        String regexUsername = "^[a-zA-Z0-9]([a-zA-Z0-9._-]){1,18}[a-zA-Z0-9]$";

        //Regex expression to check for passwords of length 8-40 with special characters
        String regexPassword = "^[a-zA-Z0-9.!@?'#$%^&-+=;()+=^._-]{8,40}$";

        //Commented version: Password regex borrowed from https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/
        //String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,40}$";

        //Email regex courtesy of Kyle Plummer
        String regexEmail = "^([0-9a-zA-Z.]+@[0-9a-zA-Z]+[.][a-zA-Z]+){1,40}$";

        String regexName = "^[a-zA-z][a-zA-z,.'-]+$";

        String regexDob = "^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$";

        //Builds a String that tells users where their inputs were incorrect
        StringBuilder message = new StringBuilder();
        if(!Pattern.matches(regexUsername, user.getUsername())){
            message.append("Username input was not valid.\n");
            //check = false;
        }
        if(!Pattern.matches(regexPassword, user.getPassword())){
            message.append("Password input was not valid.\n");
            //check = false;
        }
        if(!Pattern.matches(regexEmail, user.getEmail())){
            message.append("Email input was not valid.\n");
            //check = false;
        }
        if(!Pattern.matches(regexName, user.getFirstName())){
            message.append("First name input was not valid.\n");
            //check = false;
        }
        if(!Pattern.matches(regexName, user.getLastName())){
            message.append("Last name input was not valid.\n");
            //check = false;
        }
        if(!Pattern.matches(regexDob, user.getDob())){
            message.append("Date of birth input was not valid.\n");
            //check = false;
        }

        System.err.println(message);

        return message.toString();
    }

}
