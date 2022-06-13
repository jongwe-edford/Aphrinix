package products.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "product_log")
public class ProductLog {
    private String id;
    private String productId;
    private String userId;
    private LocalDateTime viewedAt;
    private String ipAddress;
}
