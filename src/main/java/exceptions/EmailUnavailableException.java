package exceptions;

public class EmailUnavailableException extends RuntimeException{
    public EmailUnavailableException() {
        super("The provided email is already taken!");
    }
}

