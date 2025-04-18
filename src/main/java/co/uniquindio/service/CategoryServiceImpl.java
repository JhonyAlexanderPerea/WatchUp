package co.uniquindio.service;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.model.Category;
import co.uniquindio.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Optional<CategoryResponse> createCategory(CategoryRequest categoryRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<CategoryResponse> getCategory(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<CategoryResponse> getAllCategories(String order, String name, LocalDate registerDate) {
        return Optional.empty();
    }

    @Override
    public void deleteCategory(String id) {

    }

    @Override
    public Optional<CategoryResponse> updateCategory(String id, CategoryRequest categoryRequest) {
        return Optional.empty();
    }

    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name))
                .orElseThrow(()->new RuntimeException("No se encontro la categoria: "+name));
    }
}
