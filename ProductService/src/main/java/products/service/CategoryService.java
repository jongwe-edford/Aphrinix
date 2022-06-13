package products.service;

import products.model.Category;

import java.util.List;

public interface CategoryService {

    void saveCategory(Category category);

    List<Category> findAllCategories();

    List<Category> findAllCategoriesLike(String name);

    Category findCategoryById(String id);
}
