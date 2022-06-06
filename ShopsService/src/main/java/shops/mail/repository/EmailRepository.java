package shops.mail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import shops.mail.model.Email;

public interface EmailRepository extends MongoRepository<Email,String> {
}
