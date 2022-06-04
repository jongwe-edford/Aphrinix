package shopauthservice.exception;

public class AccountWithEmailAlreadyExist extends Exception {
    public AccountWithEmailAlreadyExist(String email_exists) {
        super(email_exists);
    }
}
