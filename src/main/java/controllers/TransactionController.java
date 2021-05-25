package controllers;

import repos.*;

import javax.servlet.http.*;

public class TransactionController {

    private Repo repo;

    public TransactionController(Repo repo) {
        this.repo = repo;
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
