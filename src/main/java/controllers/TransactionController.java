package controllers;

import dtos.UserInformation;
import models.AppUser;
import models.TransactionValues;
import models.UserAccount;
import repos.*;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class TransactionController {

    private Repo repo;

    private AppUser appUser;

    private UserInformation userInformation;

    public TransactionController(Repo repo) {
        this.repo = repo;
    }


    //TODO implement validateDeposit
    public void deposit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        //Acquire parameters
        String deposit = req.getParameter("deposit");

        try {
            double deposit_am = Double.parseDouble(deposit);
            if (deposit_am < 0){
                writer.write("Please enter a valid dollar amount! Must be positive");
                resp.setStatus(400);
            } else {
                //TODO adapt to JWC
                int curr_id = appUser.getId();
                UserAccount userAccount = new UserAccount(curr_id, 0.00);
                userAccount = (UserAccount) repo.select(userAccount).get(0);
                double prev_bal = userAccount.getBalance();
                userAccount.setBalance(prev_bal + deposit_am);
                repo.update(userAccount);
                TransactionValues transactionValues = new TransactionValues(curr_id, prev_bal, deposit_am);
                repo.insert(transactionValues);

            }
        } catch (NumberFormatException e){
            writer.write("Please enter a valid dollar amount! Must be of proper format");
            resp.setStatus(400);
        }
    }
    public void withdrawal(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        //Acquire parameters
        String withdraw = req.getParameter("withdraw");

        try {
            double withdraw_am = Double.parseDouble(withdraw);
            if (withdraw_am < 0){
                resp.setStatus(400);
                writer.write("Please enter a valid dollar amount! Must be positive");
            } else{
            //TODO adapt to JWC
            int curr_id = appUser.getId();
            UserAccount userAccount = new UserAccount(curr_id, 0.00);
            userAccount = (UserAccount) repo.select(userAccount).get(0);
            double prev_bal = userAccount.getBalance();
                if (userAccount.getBalance() < withdraw_am) {
                    resp.setStatus(400);
                    writer.write("Please enter a valid dollar amount! Must be less thn your current balance");
                } else {
                    userAccount.setBalance(prev_bal-withdraw_am);
                    repo.update(userAccount);
                    TransactionValues transactionValues = new TransactionValues(curr_id, prev_bal, withdraw_am);
                    repo.insert(transactionValues);
                }
            }
        } catch (NumberFormatException e){
            resp.setStatus(400);
            writer.write("Please enter a valid dollar amount! Must be of proper format");
        }
    }
}

