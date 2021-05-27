package controllers;

import org.apache.commons.math3.util.Precision;
import repos.*;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Locale;

public class TransactionController {

    private Repo repo;

    public TransactionController(Repo repo) {
        this.repo = repo;
    }

    //TODO implement validateDeposit
    public void validateDeposit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        String amount = req.getParameter("amount");

        double deposit_am = stringCurrencyToDouble(amount);//TODO: use this to update/check balance

        String output = "Withdrew $" + deposit_am;

        if(deposit_am <= 0){
            writer.write("Invalid amount!");
        }else{
            writer.write(output);
        }
    }


    //TODO implement validateWithdrawal
    public void validateWithdrawal(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        String amount = req.getParameter("amount");

        double withdraw_am = stringCurrencyToDouble(amount);//TODO: use this to update/check balance

        String output = "Withdrew $" + withdraw_am;

        if(withdraw_am <= 0){
            writer.write("Invalid amount!");
        }else{
            writer.write(output);
        }
    }



    public String doubleToStringCurrency(double dValue){
        String sValue = NumberFormat.getCurrencyInstance(Locale.US).format(dValue);
        return sValue;
    }



    public double stringCurrencyToDouble(String sValue){
        double dValue = 0;
        String noCommas = "";

        noCommas = sValue.replace("$", "");//in case a '$' is in front of the string
        noCommas = noCommas.replaceAll(",", "");//in case large number with commas is passed

        dValue = Double.parseDouble(noCommas);
        dValue = Precision.round(dValue, 2);

        return dValue;
    }


}
