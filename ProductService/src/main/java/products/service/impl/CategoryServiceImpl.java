package products.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import products.model.Category;
import products.repository.CategoryRepository;
import products.service.CategoryService;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllCategoriesLike(String name) {
        return categoryRepository.findAllByNameIsLike(name);
    }

    @Override
    public Category findCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow();
    }
}
