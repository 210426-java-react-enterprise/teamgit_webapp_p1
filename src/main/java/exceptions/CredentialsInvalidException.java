package exceptions;

public class CredentialsInvalidException extends RuntimeException{
    public CredentialsInvalidException() {
        super("Matching username and password not found in database!");
    }
}
