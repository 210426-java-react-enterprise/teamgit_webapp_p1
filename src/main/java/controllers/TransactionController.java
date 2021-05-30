package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.DepositDTO;
import dtos.Principal;
import dtos.UserInformation;
import dtos.WithdrawDTO;
import io.jsonwebtoken.Claims;
import models.AppUser;
import models.TransactionValues;
import models.UserAccount;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import repos.*;
import security.JwtConfig;
import security.JwtService;
import services.UserService;
import utils.Logger;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

public class TransactionController {

    private final Logger logger = Logger.getLogger();
    private Repo repo;
    private UserService userService;
    private JwtService jwtService;

    public TransactionController(Repo repo, UserService userService, JwtService jwtService) {
        this.repo = repo;
        this.userService = userService;
        this.jwtService = jwtService;

    }

    public int fetchId (HttpServletRequest req) {
        jwtService.parseToken(req);
        Principal principal = (Principal) req.getAttribute("principal");

        return principal.getId();
    }

    public void balance(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");


        //AppUser.Role role = principal.getRole(); //TODO If you need it here is how you access the role

        int curr_id = fetchId(req);

        UserAccount userAccount = new UserAccount(0, curr_id, 0.00);
        userAccount = (UserAccount) repo.select(userAccount).get(0);
        double curr_bal = userAccount.getBalance();
        String balance = String.valueOf(curr_bal);
        writer.write(balance);
        resp.setStatus(200);
    }

    public void transactions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");


        int curr_id = fetchId(req);
        UserAccount userAccount = new UserAccount(0, curr_id, 0.00);

        userAccount = (UserAccount) repo.select(userAccount).get(0);

        int account_num = userAccount.getAccount_num();
        int trans_num = 0;
        Timestamp timestamp = null;
        double change = 0;
        double prev_bal = 0;


        TransactionValues transactionValues = new TransactionValues(account_num, trans_num, timestamp, change, prev_bal);
        ArrayList arrayList = repo.select(transactionValues);
        int i = 0;
        for (Object o: arrayList) {
            transactionValues = (TransactionValues) arrayList.get(i);
            change = transactionValues.getChange();
            prev_bal = transactionValues.getPrev_bal();
            double post_bal = prev_bal + change;
            timestamp = transactionValues.getTimestamp();
            trans_num = transactionValues.getTrans_id();
            account_num = transactionValues.getAccount_id();
            String type = change > 0 ? "DEPOSIT" : "WITHDRAW";
            writer.write("|| Transaction Number: " + trans_num + " || Account Number: " + account_num +
                    " || Previous Balance: " + prev_bal + " || Net Change: " + change + " || Transaction type: " + type
                    + " || Post balance: " + (post_bal) + "|| Time: " +timestamp);
            writer.write("+====================+");
            i++;
        }
        resp.setStatus(200);
    }

    public void deposit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        //Acquire parameters
        try {
            DepositDTO deposit = mapper.readValue(req.getInputStream(), DepositDTO.class);
            double deposit_am = deposit.getDeposit();
            userService.validateDeposit(deposit_am);

            int curr_id = fetchId(req);
            UserAccount userAccount = new UserAccount(0, curr_id, 0.00);
            userAccount = (UserAccount) repo.select(userAccount).get(0);
            int acc_num = userAccount.getAccount_num();
            double prev_bal = userAccount.getBalance();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            userAccount.setBalance(prev_bal + deposit_am);
            repo.update(userAccount);
            TransactionValues transactionValues = new TransactionValues(0, acc_num, timestamp, prev_bal, deposit_am);
            repo.insert(transactionValues);
            resp.setStatus(200);
        } catch (NumberFormatException | IllegalAccessException e){
            writer.write("Please enter a valid dollar amount! Must be of proper format");
            resp.setStatus(400);
        }
    }
    public void withdrawal(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        //Acquire parameters

        try {
            WithdrawDTO withdraw = mapper.readValue(req.getInputStream(), WithdrawDTO.class);
            double withdraw_am = withdraw.getWithdraw_am();
            userService.validateWithdrawPos(withdraw_am);

            jwtService.parseToken(req);
            Principal principal = (Principal) req.getAttribute("principal");
            int curr_id = principal.getId();
            UserAccount userAccount = new UserAccount(0, curr_id, 0.00);
            userAccount = (UserAccount) repo.select(userAccount).get(0);
            double prev_bal = userAccount.getBalance();
            int acc_num = userAccount.getAccount_num();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            double balance = userAccount.getBalance();

            userService.validateWithdrawBal(balance, withdraw_am);

            userAccount.setBalance(prev_bal-withdraw_am);
            repo.update(userAccount);
            TransactionValues transactionValues = new TransactionValues(0, acc_num, timestamp, prev_bal, (-1*withdraw_am));
            repo.insert(transactionValues);
            resp.setStatus(200);

        } catch (NumberFormatException | IllegalAccessException e){
            resp.setStatus(400);
            writer.write("Please enter a valid dollar amount! Must be of proper format");
        }
    }
}
