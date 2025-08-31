package com.subhash.ims.controller;

import com.subhash.ims.dto.CategoryCreateRequest;
import com.subhash.ims.dto.CategoryResponse;
import com.subhash.ims.dto.ProductResponse;
import com.subhash.ims.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Page<CategoryResponse> list(Pageable pageable) {
        return categoryService.list(pageable);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Validated @RequestBody CategoryCreateRequest req) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(req));
    }

    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable Long id,
                                @RequestParam(defaultValue = "false") boolean includeProducts) throws ChangeSetPersister.NotFoundException {
        return includeProducts ? categoryService.getWithProducts(id) : categoryService.get(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id,
                                   @Validated @RequestBody CategoryCreateRequest req) throws BadRequestException, ChangeSetPersister.NotFoundException {
        return categoryService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws Exception {
        categoryService.delete(id);
    }

    // Subresource for pagination of products under a category
    @GetMapping("/{id}/products")
    public Page<ProductResponse> listProducts(@PathVariable Long id, Pageable pageable) throws ChangeSetPersister.NotFoundException {
        return categoryService.listProducts(id, pageable);
    }
}
