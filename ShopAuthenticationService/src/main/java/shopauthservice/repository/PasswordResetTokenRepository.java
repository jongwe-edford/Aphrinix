package shopauthservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import shopauthservice.models.AccountActivationToken;
import shopauthservice.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken,String> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByEmail(String userId);
}
