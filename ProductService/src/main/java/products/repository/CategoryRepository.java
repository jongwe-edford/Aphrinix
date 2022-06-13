package products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import products.model.Category;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category,String> {
    List<Category> findAllByNameIsLike(String name);
}
