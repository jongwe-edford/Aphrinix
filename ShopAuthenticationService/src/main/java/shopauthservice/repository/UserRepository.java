package shopauthservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shopauthservice.models.User;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
