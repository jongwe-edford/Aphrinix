package products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import products.model.Review;
import products.model.User;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review,String> {
    Review findByUser(User user);
    void deleteByUser(User user);
    List<Review> findAllByProductId(String productId);
}
