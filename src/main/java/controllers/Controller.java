package controllers;



import models.*;
import repos.*;
import services.*;

import javax.servlet.http.*;





public class Controller {

    UserRepo userRepo = new UserRepo();
    UserService userService = new UserService(userRepo);

    //TODO implement register
    public void register(HttpServletRequest req, HttpServletResponse resp) {

        AppUser user = new AppUser();
        userService.isUserValid(user);

    }

    //TODO implement authenticate
    public void authenticate(HttpServletRequest req, HttpServletResponse resp) {
        String username = "";
        String password = "";
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        userService.authenticateUserCredentials(user);
    }

    //TODO implement validateDeposit
    public void validateDeposit(HttpServletRequest req, HttpServletResponse resp) {
        double deposit_am = 0.0;
        userService.depositVerify(deposit_am);
    }

    //TODO implement validateWithdrawal
    public void validateWithdrawal(HttpServletRequest req, HttpServletResponse resp) {
        double withdraw_am = 0.0;
        userService.withdrawVerify(withdraw_am);
    }

    //public void isUsername

}
