package shopauthservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
@Data
@Document(collection = "password_reset_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetToken {
    private String id;
    private String email;
    private String token;
    private Instant expiration;
    private Instant createdAt;
    private boolean used;
}
