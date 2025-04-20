package co.uniquindio.service;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.dtos.response.PaginatedCategoryResponse;
import co.uniquindio.model.Category;
import java.time.LocalDate;
import java.util.Optional;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    Optional<CategoryResponse> getCategory(String id);
    PaginatedCategoryResponse getAllCategories(String order, String name,int page);
    void deleteCategory(String id);
    Optional<CategoryResponse> updateCategory(String id, CategoryRequest categoryRequest);
    
    Category getCategoryByName(String name);
    
}
