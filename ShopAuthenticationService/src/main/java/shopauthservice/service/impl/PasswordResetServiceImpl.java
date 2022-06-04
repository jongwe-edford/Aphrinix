package shopauthservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopauthservice.exception.PasswordResetTokenExpiredException;
import shopauthservice.exception.UserNotFoundException;
import shopauthservice.models.AccountActivationToken;
import shopauthservice.models.PasswordResetToken;
import shopauthservice.models.User;
import shopauthservice.payload.request.PasswordResetRequest;
import shopauthservice.repository.PasswordResetTokenRepository;
import shopauthservice.repository.UserRepository;
import shopauthservice.service.PasswordResetService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void forgotPassword(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No account exist"));
        PasswordResetToken passwordResetToken = PasswordResetToken
                .builder()
                .createdAt(Instant.now())
                .expiration(Instant.now().plus(15, ChronoUnit.MINUTES))
                .token(UUID.randomUUID().toString())
                .email(user.getEmail())
                .build();
        passwordResetTokenRepository.save(passwordResetToken);
        System.out.println(passwordResetToken.getToken());
    }

    @Override
    public void resetPassword(String token, PasswordResetRequest passwordResetRequest) throws PasswordResetTokenExpiredException, UserNotFoundException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (!validatePasswordToken(passwordResetToken))
            throw new PasswordResetTokenExpiredException("Token expired");
        User user = userRepository
                .findByEmail(
                        passwordResetToken
                                .getEmail())
                .orElseThrow(() -> new UserNotFoundException("No account exist")
                );
        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void resendResetToken(String email) throws UserNotFoundException {
        if (!userRepository.existsByEmail(email))
            throw new IllegalArgumentException("No account exists");
        PasswordResetToken passwordResetToken =   passwordResetTokenRepository.findAll( Sort.by(Sort.Direction.DESC,"expiration"))
                .stream()
                .filter(resetToken -> resetToken.getEmail().equals(email))
                .collect(Collectors.toList()).get(0);
        if (passwordResetToken == null) {
            passwordResetToken = PasswordResetToken
                    .builder()
                    .expiration(Instant.now().plus(15, ChronoUnit.MINUTES))
                    .createdAt(Instant.now())
                    .token(UUID.randomUUID().toString())
                    .email(email)
                    .build();
            System.out.println("Token::--->" + passwordResetToken.getToken());
            forgotPassword(email);
        } else {
            if (!validatePasswordToken(passwordResetToken)) {
                passwordResetToken.setExpiration(Instant.now().plus(15, ChronoUnit.MINUTES));
                forgotPassword(email);
            }
        }
    }

    @Override
    public boolean validatePasswordToken(PasswordResetToken passwordResetToken) {
        return passwordResetToken.getExpiration().compareTo(Instant.now()) > 0;
    }
}
