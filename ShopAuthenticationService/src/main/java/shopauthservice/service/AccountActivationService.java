package shopauthservice.service;

import shopauthservice.models.AccountActivationToken;

import javax.swing.*;

public interface AccountActivationService {
    void saveToken(AccountActivationToken activationToken);

    boolean verifyActivationToken(AccountActivationToken activationToken);
    AccountActivationToken getAccountActivationTokenFromToken(String token);
    void resendActivationToken(String email);
}
