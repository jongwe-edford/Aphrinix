package shops.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import shops.model.enums.UserRole;

@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private String id;
    private UserRole role;
}
