package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.dtos.response.PaginatedCategoryResponse;
import co.uniquindio.exceptions.FoundMatchException;
import co.uniquindio.exceptions.NotFoundException;
import co.uniquindio.mappers.CategoryMapper;
import co.uniquindio.model.Category;
import co.uniquindio.repository.CategoryRepository;
import co.uniquindio.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            throw new FoundMatchException("Ya existe una categoria con el nombre: "+category.getName());
        }
        return categoryMapper.toResponse(categoryRepository.save(category)) ;
    }

    @Override
    public Optional<CategoryResponse> getCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("No se encontro la categoria con el id: "+id
                        +"\nPuede de que no exista o se haya eliminado de la base de datos"));
        return Optional.ofNullable(categoryMapper.toResponse(category));
    }

    @Override
    public PaginatedCategoryResponse getAllCategories(String order, String name, int page) {
        Pageable pageable = PageRequest.of(page, 10, parseSort(order));

        List<Category> auxList = new ArrayList<>();

        if (name != null && !name.isEmpty()) auxList = categoryRepository.findCategoriesByNameContainingOrderByCreationDateAsc(name ,pageable);
        else auxList = categoryRepository.findAll();

        if (!auxList.isEmpty()) {
            return new PaginatedCategoryResponse(
                    auxList.stream().map(categoryMapper::toResponse).toList(),
                    new PaginatedContent((auxList.size()+9)/10, auxList.size(), page, 10)
            );
        }
        throw new NotFoundException("No se encontro ninguna categoria, comunicarse con el equipo por favor");

    }

    private Sort parseSort(String order) {
        if (order == null || order.isEmpty()) return Sort.unsorted();
        String[] parts = order.split(":");
        return Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
    }

    @Override
    public void deleteCategory(String id) {
        if(categoryRepository.findById(id).isEmpty()){
            throw new NotFoundException("No se encontro la categoria con el id: "+id
                    +"\nPuede de que no exista o se haya eliminado de la base de datos");
        }
        categoryRepository.deleteById(id);
        if(categoryRepository.findById(id).isPresent()){
            throw new RuntimeException("No se pudo eliminar la categoria con el id: "+id);
        }
    }

    @Override
    public Optional<CategoryResponse> updateCategory(String id, CategoryRequest categoryRequest) {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(()->new NotFoundException("No se encontro la categoria con el id: "+id
                        +"\nPuede de que no exista o se haya eliminado de la base de datos")) ;
        if(categoryRequest.name()!=null && !categoryRequest.name().isEmpty()){
            if(categoryRepository.findByName(category.getName())!=null){
                throw new FoundMatchException("Ya existe una categoria con el nombre: "+category.getName());
            }else{
                category.setName(categoryRequest.name());
            }
        }
        if(categoryRequest.description()!=null && !categoryRequest.description().isEmpty()){
            category.setDescription(categoryRequest.description());
        }
        if(categoryRepository.findById(id).isPresent()){
            return Optional.ofNullable(categoryMapper.toResponse(categoryRepository.save(category)));
        }else throw new NotFoundException("No se encontro la categoria con el id: "+id
                                        +" y no se pudo actualizar");
    }

    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name))
                .orElseThrow(()->new NotFoundException("No se encontro la categoria: "+name));
    }
}
