package com.subhash.ims.service;

import com.subhash.ims.dto.ProductCreateRequest;
import com.subhash.ims.dto.ProductResponse;
import com.subhash.ims.dto.ProductUpdateRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponse> list(Pageable pageable);
    Page<ProductResponse> listByCategory(Long categoryId, Pageable pageable);
    ProductResponse create(ProductCreateRequest req) throws BadRequestException, ChangeSetPersister.NotFoundException;
    ProductResponse get(Long id) throws ChangeSetPersister.NotFoundException;
    ProductResponse update(Long id, ProductUpdateRequest req) throws ChangeSetPersister.NotFoundException;
    void delete(Long id);
}
