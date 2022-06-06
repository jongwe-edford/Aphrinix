package shopauthservice.service;

import org.springframework.http.ResponseEntity;
import shopauthservice.exception.AccountWithEmailAlreadyExist;
import shopauthservice.exception.PasswordResetTokenExpiredException;
import shopauthservice.exception.RefreshTokenExpired;
import shopauthservice.exception.UserNotFoundException;
import shopauthservice.models.User;
import shopauthservice.payload.request.LoginRequest;
import shopauthservice.payload.request.PasswordResetRequest;
import shopauthservice.payload.request.RegistrationRequest;
import shopauthservice.payload.response.LoginResponse;

import javax.security.auth.login.AccountNotFoundException;

public interface UserService {
    String register(RegistrationRequest request) throws AccountWithEmailAlreadyExist;

    ResponseEntity<?> login(LoginRequest loginRequest);
    User getUserByEmail(String email) throws AccountNotFoundException;

    String activateAccount(String token);

    User getCurrentUserFromToken();

    User getUser(String token) throws UserNotFoundException;
    void forgotPassword(String email) throws UserNotFoundException;

    void resendPasswordResetToken(String email) throws UserNotFoundException;

    void resetPassword(String email, PasswordResetRequest passwordResetRequest) throws UserNotFoundException, PasswordResetTokenExpiredException;

    void resendActivationToken(String email);
    LoginResponse refreshToken(String token) throws RefreshTokenExpired;


}
