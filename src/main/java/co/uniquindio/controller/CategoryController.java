package co.uniquindio.controller;

import co.uniquindio.dtos.request.CategoryRequest;
import co.uniquindio.dtos.response.CategoryResponse;
import co.uniquindio.dtos.response.PaginatedCategoryResponse;
import co.uniquindio.repository.CategoryRepository;
import co.uniquindio.service.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        var categoryResponse = categoryService.createCategory(categoryRequest);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(categoryResponse.id()).
                toUri();
        return ResponseEntity.created(location).body(categoryResponse);
    }
    @GetMapping
    public PaginatedCategoryResponse getAllCategories(@RequestParam(required = false) String order,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) @DefaultValue(value = "0") Integer page){
        int currentPage = (page != null) ? page : 0;
        return categoryService.getAllCategories(order,name, currentPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String id){
        return categoryService.getCategory(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable String id, @RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(id, categoryRequest).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id){
        categoryService.deleteCategory(id);
    }

}
