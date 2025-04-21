package co.uniquindio.mappers;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.model.Comment;
import co.uniquindio.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {UserRepository.class})
public interface CommentMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "reportId", ignore = true)
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "ACTIVE")
    Comment parseOf(CommentRequest commentRequest);

    @Mapping(target= "userName", ignore = true)
    @Mapping(target= "date", source = "creationDate")
    CommentResponse toResponse(Comment comment);


}
