package shopauthservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "refresh-tokens")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefreshToken {
    private String id;
    private String email;
    private String token;
    private Instant expiration;
    private Instant createdAt;
}
