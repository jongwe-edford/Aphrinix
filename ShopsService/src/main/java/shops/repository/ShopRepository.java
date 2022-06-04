package shops.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import shops.model.Shop;
import shops.model.User;

import java.util.List;

public interface ShopRepository extends MongoRepository<Shop, String> {
    Shop findByName(String name);
    Shop findByShopId(String shopId);
    Shop findByShopAdmin(User admin);
    Boolean existsByShopAdmin(User admin);

}
