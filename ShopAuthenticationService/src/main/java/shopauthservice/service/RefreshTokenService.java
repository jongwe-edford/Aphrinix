package shopauthservice.service;

import shopauthservice.exception.RefreshTokenExpired;
import shopauthservice.models.RefreshToken;
import shopauthservice.payload.response.LoginResponse;

public interface RefreshTokenService {
    String generateToken(String email);

    boolean validateToken(RefreshToken token);

    LoginResponse refreshToken(String token) throws RefreshTokenExpired;
}
