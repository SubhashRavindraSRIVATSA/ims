package com.subhash.ims.controller;

import com.subhash.ims.dto.ProductCreateRequest;
import com.subhash.ims.dto.ProductResponse;
import com.subhash.ims.dto.ProductUpdateRequest;
import com.subhash.ims.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public Page<ProductResponse> list(Pageable pageable,
                                      @RequestParam(required = false) Long categoryId) {
        return (categoryId == null)
                ? productService.list(pageable)
                : productService.listByCategory(categoryId, pageable);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Validated @RequestBody ProductCreateRequest req) throws BadRequestException, ChangeSetPersister.NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(req));
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return productService.get(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id,
                                  @Validated @RequestBody ProductUpdateRequest req) throws ChangeSetPersister.NotFoundException {
        return productService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
