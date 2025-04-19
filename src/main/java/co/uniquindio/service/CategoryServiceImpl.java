package co.uniquindio.service;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.dtos.response.PaginatedCategoryResponse;
import co.uniquindio.mappers.CategoryMapper;
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
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        System.out.println(categoryRequest.name());
        Category category = categoryMapper.parseOf(categoryRequest);
        System.out.println(category.getName());
        if(categoryRepository.findByName(category.getName())!=null){
            throw new RuntimeException("Ya existe una categoria con el nombre: "+category.getName());
        }
        return categoryMapper.toResponse(categoryRepository.save(category)) ;
    }

    @Override
    public Optional<CategoryResponse> getCategory(String id) {
        return Optional.empty();
    }

    @Override
    public PaginatedCategoryResponse getAllCategories(String order, String name, LocalDate registerDate) {
        return null;
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
