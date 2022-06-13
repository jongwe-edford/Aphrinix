package products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import products.model.ProductLog;

import java.util.List;

public interface ProductLogRepository extends MongoRepository<ProductLog, String> {
    List<ProductLog> findAllByUserId(String userId);

    List<ProductLog> findAllByProductId(String productId);
}
