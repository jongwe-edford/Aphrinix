package shopauthservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import shopauthservice.models.RefreshToken;

import java.util.List;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String> {
    RefreshToken findByToken(String token);
    RefreshToken findByEmail(String email);
    List<RefreshToken> findAllByEmail(String email);
}
