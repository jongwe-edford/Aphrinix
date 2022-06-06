package shops.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import shops.model.ShopManager;

public interface ShopManagerRepository extends MongoRepository<ShopManager ,String> {
    Boolean existsByEmail(String email);
    Boolean existsByShopId(String shopId);
}
