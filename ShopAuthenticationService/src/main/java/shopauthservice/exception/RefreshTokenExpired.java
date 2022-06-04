package shopauthservice.exception;

public class RefreshTokenExpired extends Throwable {
    public RefreshTokenExpired(String token_expired) {
        super(token_expired);
    }
}
