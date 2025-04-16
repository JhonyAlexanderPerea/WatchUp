package co.uniquindio.repository;

import co.uniquindio.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository <Category,String> {

}
