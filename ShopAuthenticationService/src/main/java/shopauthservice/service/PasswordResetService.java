package shopauthservice.service;

import shopauthservice.exception.PasswordResetTokenExpiredException;
import shopauthservice.exception.UserNotFoundException;
import shopauthservice.models.PasswordResetToken;
import shopauthservice.payload.request.PasswordResetRequest;

public interface PasswordResetService {
    void forgotPassword(String email) throws UserNotFoundException;
    void resetPassword(String token, PasswordResetRequest passwordResetRequest) throws PasswordResetTokenExpiredException, UserNotFoundException;
    void resendResetToken(String email) throws UserNotFoundException;
    boolean validatePasswordToken(PasswordResetToken passwordResetToken);
}
