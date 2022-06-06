package shops.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shop_manager")
public class ShopManager {
    String firstname;
    String lastname;
    String email;
    String photoUrl;
    String shopId;
}
