package co.uniquindio.repository;

import co.uniquindio.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("""
        {
            $and: [
                { 'fullName': { $regex: ?0, $options: 'i' } },
                { 'email': ?1 },
                { 'registerDate': ?2 },
                { 'age': ?3 },
                { 'status': ?4 }
            ]
        }
        """)
    Page<User> findByFilters(
            String fullName,
            String email,
            LocalDate registerDate,
            Integer age,
            String status,
            Pageable pageable
    );

    boolean existsByEmail(String email);

    User getUserById(String id);
}
