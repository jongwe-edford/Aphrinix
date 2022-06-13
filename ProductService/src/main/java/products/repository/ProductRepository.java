package products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import products.model.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByShopId(String shopId);

}
