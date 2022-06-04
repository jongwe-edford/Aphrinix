package shopauthservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import shopauthservice.models.enums.UserRole;

@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private String id;
    private UserRole role;

    public Role(UserRole role) {
        this.role = role;
    }
}
