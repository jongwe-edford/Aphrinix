package products.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
    private String photoUrl;
    private String shopId;
    private boolean enabled=true;
    private Set<String> roles;

}
