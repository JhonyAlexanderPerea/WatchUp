package co.uniquindio.repository;

import co.uniquindio.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository <Comment, String> {
    
}
