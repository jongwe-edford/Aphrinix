package shopauthservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.RestController;
import shopauthservice.models.Role;
import shopauthservice.models.User;
import shopauthservice.models.enums.UserRole;

import java.util.Optional;

@RestController
public interface RoleRepository extends MongoRepository<Role,String> {
    Role findByRole(UserRole role);
}
