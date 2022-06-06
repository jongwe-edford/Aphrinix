package shops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "shop_manager")
public class ShopManager {
    @Id
    String id;
    String firstname;
    String lastname;
    String email;
    String photoUrl;
    String shopId;
}
