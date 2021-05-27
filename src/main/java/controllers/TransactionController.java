package controllers;

import repos.*;
import services.*;

import javax.servlet.http.*;

public class TransactionController {

    private UserService userService;

    public TransactionController(UserService userService) {
        this.userService = userService;
    }

    //TODO implement validateDeposit
    public void validateDeposit(HttpServletRequest req, HttpServletResponse resp) {
        double deposit_am = 0.0;
    }

    //TODO implement validateWithdrawal
    public void validateWithdrawal(HttpServletRequest req, HttpServletResponse resp) {
        double withdraw_am = 0.0;
    }

}
