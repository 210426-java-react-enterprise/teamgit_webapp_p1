package services;

import exceptions.NegativeDepositException;
import exceptions.NegativeWithdrawalException;
import repos.Repo;

public class TransactionService {
    repos.Repo repo = new Repo();

    public void validateDeposit(double deposit_am) {
        if (deposit_am < 0) {
            throw new NegativeDepositException();
        }
    }

    public void validateWithdrawPos(double withdraw_am) {
        if (withdraw_am < 0) {
            throw new NegativeWithdrawalException();
        }
    }

    public void validateWithdrawBal(double balance, double withdraw_am) {
        if (withdraw_am > balance) {
            throw new NegativeWithdrawalException();
        }
    }
}
