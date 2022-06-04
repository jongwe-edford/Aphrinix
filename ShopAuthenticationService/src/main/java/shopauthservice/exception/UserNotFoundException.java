package shopauthservice.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String no_account_exist) {
        super(no_account_exist);
    }
}
