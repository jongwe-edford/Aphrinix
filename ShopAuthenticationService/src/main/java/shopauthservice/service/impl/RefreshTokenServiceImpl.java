package shopauthservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import shopauthservice.exception.RefreshTokenExpired;
import shopauthservice.models.RefreshToken;
import shopauthservice.models.User;
import shopauthservice.payload.response.LoginResponse;
import shopauthservice.repository.RefreshTokenRepository;
import shopauthservice.repository.UserRepository;
import shopauthservice.service.RefreshTokenService;
import shopauthservice.util.JwtUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(String email) {
        RefreshToken refreshToken = RefreshToken
                .builder()
                .email(email)
                .token(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .expiration(Instant.now().plus(6, ChronoUnit.DAYS))
                .build();
        repository.save(refreshToken);
        return refreshToken.getToken();
    }

    @Override
    public boolean validateToken(RefreshToken token) {
        return token.getExpiration().compareTo(Instant.now()) < 0;
    }

    @Override
    public LoginResponse refreshToken(String token) throws RefreshTokenExpired {
        RefreshToken refreshToken = repository.findByToken(token);
        if (validateToken(refreshToken))
            throw new RefreshTokenExpired("Token expired");
        User user = userRepository.findByEmail(refreshToken.getEmail()).orElseThrow();
        ResponseCookie responseCookie = jwtUtil.generateJwtCookie(user);
        return LoginResponse
                .builder()
                .accessToken(responseCookie.getValue())
                .refreshToken(generateToken(user.getEmail()))
                .roles(user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList()))
                .build();
    }
}
