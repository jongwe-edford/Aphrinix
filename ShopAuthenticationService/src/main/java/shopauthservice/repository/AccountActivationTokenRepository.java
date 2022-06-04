package shopauthservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import shopauthservice.models.AccountActivationToken;

public interface AccountActivationTokenRepository extends MongoRepository<AccountActivationToken, String> {
    AccountActivationToken findByToken(String token);

    AccountActivationToken findByEmail(String userId);
}
