package exceptions;

public class UsernameUnavailableException extends RuntimeException{
    public UsernameUnavailableException() {
        super("The provided username is already taken!");
    }
}
