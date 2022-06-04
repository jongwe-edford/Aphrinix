package shopauthservice.exception;

public class PasswordResetTokenExpiredException extends Throwable {
    public PasswordResetTokenExpiredException(String token_expired) {
        super(token_expired);
    }
}
