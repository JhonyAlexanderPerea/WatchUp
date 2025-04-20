package co.uniquindio.repository;

import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findCategoriesByNameContaining(String name, Pageable pageable);
    List<Category> findCategoriesByNameContainingOrderByCreationDateAsc(String name, Pageable pageable);


    Category findByName(String name);
}
