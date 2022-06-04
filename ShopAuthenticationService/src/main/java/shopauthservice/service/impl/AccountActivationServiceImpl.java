package shopauthservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shopauthservice.models.AccountActivationToken;
import shopauthservice.repository.AccountActivationTokenRepository;
import shopauthservice.repository.UserRepository;
import shopauthservice.service.AccountActivationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountActivationServiceImpl implements AccountActivationService {
    private final AccountActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void saveToken(AccountActivationToken activationToken) {
        activationTokenRepository.save(activationToken);
        System.out.println("Token::-->>" + activationToken.getToken());
        //:todo send activation email
    }

    @Override
    public boolean verifyActivationToken(AccountActivationToken activationToken) {
        return activationToken.getExpiration().compareTo(Instant.now()) < 0;
    }

    @Override
    public AccountActivationToken getAccountActivationTokenFromToken(String token) {
        return activationTokenRepository.findByToken(token);
    }

    @Override
    public void resendActivationToken(String email) {
        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("No account exists");

        AccountActivationToken accountActivationToken = activationTokenRepository.findByEmail(email);
        if (accountActivationToken == null) {
            accountActivationToken = AccountActivationToken
                    .builder()
                    .expiration(Instant.now().plus(15, ChronoUnit.MINUTES))
                    .createdAt(Instant.now())
                    .token(UUID.randomUUID().toString())
                    .email(email)
                    .build();
            System.out.println("Token::--->" + accountActivationToken.getToken());
            saveToken(accountActivationToken);
        } else {
            if (!verifyActivationToken(accountActivationToken)) {
                accountActivationToken.setExpiration(Instant.now().plus(15, ChronoUnit.MINUTES));
                saveToken(accountActivationToken);
            }
        }
    }
}
