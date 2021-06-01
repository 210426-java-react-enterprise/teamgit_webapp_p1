package exceptions;

public class NegativeWithdrawalException extends RuntimeException{
        public NegativeWithdrawalException(){
            super("You can only withdraw positive values!");
        }
    }

