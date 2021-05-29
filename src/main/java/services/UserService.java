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


    /**
     * Varifies that the user can be registered, ensuring the username and email isn't already taken.
     * @param appUser AppUser
     * @return An ArrayList of Objects that should be AppUsers.  Should only be 1 object in the returned list.
     */
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


    /**
     * Checks username and password in the database and verifies that they are both in the same row (i.e. they match).
     * @param username String
     * @param password String
     * @return true if credentials match what's in database, otherwise false
     */
    public AppUser authenticateUserCredentials(String username, String password){
        if(username == null || password == null){
            throw new AuthenticationException("Your username or password was invalid!");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(password);

        ArrayList<Object> registeredUser = repo.select(appUser);

        if(registeredUser.size() < 1){
            throw new AuthenticationFailedException("Your entered credentials do not match our records!");
        }

        AppUser selectResult = (AppUser) registeredUser.get(0);

        //would have matching password at this point
        if(selectResult.getUsername().equals(username) && selectResult.getPassword().equals(password)){
            selectResult.setRole(AppUser.Role.BASIC_USER);
            return selectResult;
        }

        return null;
    }


    /**
     * Verifies that user can be deleted by checking that there is a provided username or email.
     * @param appUser AppUser which must contain a username and/or email.
     * @return true if user data was deleted, false otherwise
     * @throws IllegalAccessException
     */
    public boolean verifyDeletion(AppUser appUser) throws IllegalAccessException {
        //fields that a regular user must have at least one of
        String username = null;
        String email = null;

        if(appUser.getUsername() != null){
            username = appUser.getUsername();
        }

        if(appUser.getEmail() != null){
            email = appUser.getEmail();
        }

        if(username != null || email != null){//1 value must be unique
            //if at least 1 row was deleted, return true, else return false
            return (repo.delete(appUser) > 0);
        }

        return false;
    }



    /**
     * verifies that the deposit is positive, if it is it will invoke the deposit
     * @param deposit_am
     */
    //TODO: needs implementation
    public void verifyDeposit(double deposit_am){
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
    //TODO implement
    public void verifyWithdrawal(double withdraw_am){
//        if (withdraw_am < 0 || withdraw_am > AccountRepo.fetchBalance(getId())) {
//            System.out.println("Withdrawal value must be greater than zero and less than account balance!");
//        } else{
//            //AccountsDAO.withdraw(getId(), withdraw_am);
//        }
    }

    //TODO implementation

    /**
     * Check sif username is available.
     * @param username String
     * @return true if username is available, false if it's already taken
     */
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

    /**
     * Checks the database if the email is already use or not.
     * @param email String
     * @return true if email is available, false if it's already taken.
     */
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

    /**
     * Checks if given AppUser has valid fields.
     *
     * @param user AppUserw with a username, password, email, first name, last name, and date of birth.
     * @return a String error message, if any errors are found.
     *
     */
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
