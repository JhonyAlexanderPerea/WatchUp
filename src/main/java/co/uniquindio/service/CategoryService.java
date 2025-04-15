package co.uniquindio.service;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.dtos.response.PaginatedCategoriesResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    PaginatedCategoriesResponse getCategories (int page, int size);
    CategoryResponse getCategory (String id);
    CategoryResponse updateCategory (String id, CategoryRequest request);
    void deleteCategory (String id);
}
