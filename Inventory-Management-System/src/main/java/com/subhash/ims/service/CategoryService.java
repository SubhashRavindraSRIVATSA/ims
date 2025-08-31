package com.subhash.ims.service;

import com.subhash.ims.dto.CategoryCreateRequest;
import com.subhash.ims.dto.CategoryResponse;
import com.subhash.ims.dto.ProductResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponse> list(Pageable pageable);
    CategoryResponse create(CategoryCreateRequest req) throws BadRequestException;
    CategoryResponse get(Long id) throws ChangeSetPersister.NotFoundException;                    // no products
    CategoryResponse getWithProducts(Long id) throws ChangeSetPersister.NotFoundException;        // embed products
    CategoryResponse update(Long id, CategoryCreateRequest req) throws BadRequestException, ChangeSetPersister.NotFoundException;
    void delete(Long id) throws Exception;
    Page<ProductResponse> listProducts(Long categoryId, Pageable pageable) throws ChangeSetPersister.NotFoundException;
}
