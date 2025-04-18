package co.uniquindio.mappers;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "id", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "creationDate", defaultExpression = "java(java.time.LocalDate.now())")
    Category parseOf(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);
}
