package co.uniquindio.service;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.model.Category;
import java.time.LocalDate;
import java.util.Optional;

public interface CategoryService {
    Optional<CategoryResponse> createCategory(CategoryRequest categoryRequest);
    Optional<CategoryResponse> getCategory(String id);
    Optional<CategoryResponse> getAllCategories(String order, String name, LocalDate registerDate);
    void deleteCategory(String id);
    Optional<CategoryResponse> updateCategory(String id, CategoryRequest categoryRequest);
    
    Category getCategoryByName(String name);
    
}
