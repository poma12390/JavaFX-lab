package lab6.common.exceptions;

public class AuthorizationException extends RuntimeException{

    public AuthorizationException() {
    super("you should be authorized");
    }
}
