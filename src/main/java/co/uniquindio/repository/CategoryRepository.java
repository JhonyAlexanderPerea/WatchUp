package co.uniquindio.repository;

import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String name);
}
