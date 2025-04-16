package co.uniquindio.repository;

import co.uniquindio.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Page<User> findAll(Pageable pageable);

    Optional<Object> findByEmail(String email);
}
