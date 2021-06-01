package exceptions;

public class NegativeDepositException extends RuntimeException{
    public NegativeDepositException(){
        super("You can only deposit positive values!");
    }
}
