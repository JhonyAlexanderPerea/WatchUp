package co.uniquindio.mappers;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.model.Category;
import co.uniquindio.service.CategoryService;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CategoryService.class})
public interface CategoryMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDate.now())")
    Category parseOf(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

}
