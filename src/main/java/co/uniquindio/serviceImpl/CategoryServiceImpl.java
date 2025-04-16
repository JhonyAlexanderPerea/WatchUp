package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.dtos.response.PaginatedCategoriesResponse;
import co.uniquindio.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        //TODO
        return null;
    }

    @Override
    public PaginatedCategoriesResponse getCategories(int page, int size) {
        //TODO
        return null;
    }

    @Override
    public CategoryResponse getCategory(String id) {
        //TODO
        return null;
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest request) {
        //TODO
        return null;
    }

    @Override
    public void deleteCategory(String id) {
        //TODO
    }
}
