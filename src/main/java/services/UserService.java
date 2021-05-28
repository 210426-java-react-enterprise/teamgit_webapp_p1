package services;

import exceptions.*;
import models.*;
import repos.*;

import java.util.*;
import java.util.regex.*;

public class UserService {

    private Repo repo;

    public UserService(Repo repo){
        this.repo = repo;
    }

    public ArrayList<Object> verifyRegistration(AppUser appUser){
        ArrayList<Object> registeredUser = null;

        //checks if inputs are valid, throws exception with message generated by isUserValdi() otherwise
        String message = isUserValid(appUser);
        if(!message.equals("")){
            throw new ResourceInvalidException(message);
        }

        if(!isUsernameAvailable(appUser.getUsername())){
            throw new UsernameUnavailableException();
        }

        if(!isEmailAvailable(appUser.getEmail())){
            throw new EmailUnavailableException();
        }

        repo.create(appUser);
        repo.insert(appUser);
        registeredUser = repo.select(appUser);
        return registeredUser;
    }

    //TODO authenticate username and password
    public boolean authenticateUserCredentials(AppUser appUser){

        //might need another service method findUserByUsernameAndPassword()

        return false;
    }

    public boolean validateDeposit(double deposit_am) {
        if (deposit_am < 0) {
            throw new NegativeDepositException();
        }
        return true;
    }

    public boolean validateWithdrawPos(double withdraw_am) {
        if (withdraw_am < 0) {
            throw new NegativeWithdrawalException();
        }
        return true;
    }

    public boolean validateWithdrawBal(double balance, double withdraw_am) {
        if (withdraw_am > balance) {
            throw new NegativeWithdrawalException();
        }
        return true;
    }

    //TODO implementation
    public boolean isUsernameAvailable(String username){
        boolean check = false;
        AppUser user = new AppUser();
        user.setUsername(username);
        ArrayList<Object> result = repo.select(user);
        if(result.size() == 0){
            check = true;
        }

        return check;
    }

    public boolean isEmailAvailable(String email){
        boolean check = false;
        AppUser user = new AppUser();
        user.setEmail(email);
        ArrayList<Object> result = repo.select(user);
        if(result.size() == 0){
            check = true;
        }

        return check;
    }

    public String isUserValid(AppUser user) {

        int numcheck = 0;

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
            message.append("Username, ");
            numcheck++;

        }
        if(!Pattern.matches(regexPassword, user.getPassword())){
            message.append("Password, ");
            numcheck++;
        }
        if(!Pattern.matches(regexEmail, user.getEmail())){
            message.append("Email, ");
            numcheck++;
        }
        if(!Pattern.matches(regexName, user.getFirstName())){
            message.append("First name, ");
            numcheck++;
        }
        if(!Pattern.matches(regexName, user.getLastName())){
            message.append("Last name, ");
            numcheck++;
        }
        if(!Pattern.matches(regexDob, user.getDob())){
            message.append("Date of birth, ");
            numcheck++;
        }
        if(numcheck > 0) {
            message.append("input(s) were not valid.\n");
            message.deleteCharAt(message.lastIndexOf(","));
        }

        return message.toString();
    }

}