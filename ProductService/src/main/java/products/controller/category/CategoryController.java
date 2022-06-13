package products.controller.category;

import org.springframework.http.ResponseEntity;
import products.model.Category;

import java.util.List;

public interface CategoryController {

    ResponseEntity<Void> saveCategory(Category category);

    ResponseEntity<List<Category>> findCategoriesLike(String name);

    ResponseEntity<List<Category>> findAllCategories();

    ResponseEntity<Category> findCategoryById(String id);

}
