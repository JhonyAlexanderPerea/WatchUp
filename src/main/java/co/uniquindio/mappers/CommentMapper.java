package co.uniquindio.mappers;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Mapping(target = "id", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "creationDate", defaultExpression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", defaultValue = "ACTIVE")
    Comment parseOf(CommentRequest commentRequest);

    CommentResponse toResponse(Comment comment);
}
