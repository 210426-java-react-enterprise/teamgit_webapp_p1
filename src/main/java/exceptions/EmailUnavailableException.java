package exceptions;

public class EmailUnavailableException extends RuntimeException{
    public EmailUnavailableException() {
        super("You already registered with the provided email!");
    }
}

