package com.subhash.ims.service.impl;

import com.subhash.ims.Repository.CategoryRepository;
import com.subhash.ims.Repository.ProductRepository;
import com.subhash.ims.dto.ProductCreateRequest;
import com.subhash.ims.dto.ProductResponse;
import com.subhash.ims.dto.ProductUpdateRequest;
import com.subhash.ims.model.Category;
import com.subhash.ims.model.Product;
import com.subhash.ims.service.ProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository products;

    @Autowired
    CategoryRepository categories;

    @Override
    public Page<ProductResponse> list(Pageable pageable) {
        return products.findAll(pageable).map(this::toResponse);
    }

    @Override
    public Page<ProductResponse> listByCategory(Long categoryId, Pageable pageable) {
        return products.findAllByCategory_Id(categoryId, pageable).map(this::toResponse);
    }

    @Override
    public ProductResponse create(ProductCreateRequest req) throws BadRequestException, ChangeSetPersister.NotFoundException {
        Category cat = categories.findById(req.categoryId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (products.findBySku(req.sku()).isPresent()) {
            throw new BadRequestException("SKU already exists");
        }
        Product p = new Product();
        p.setName(req.name());
        p.setSku(req.sku());
        p.setPrice(req.price());
        p.setQuantityType(req.quantityType());
        p.setCategory(cat);
        products.save(p);
        return toResponse(p);
    }

    @Override
    public ProductResponse get(Long id) throws ChangeSetPersister.NotFoundException {
        return toResponse(find(id));
    }

    @Override
    public ProductResponse update(Long id, ProductUpdateRequest req) throws ChangeSetPersister.NotFoundException {
        Product p = find(id);
        Category cat = categories.findById(req.categoryId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        p.setName(req.name());
        p.setPrice(req.price());
        p.setQuantityType(req.quantityType());
        p.setCategory(cat);
        products.save(p);
        return toResponse(p);
    }

    @Override
    public void delete(Long id) {
        products.deleteById(id);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(), p.getName(), p.getSku(), p.getPrice(),
                p.getQuantityType(), p.getCategory().getId(), p.getCategory().getName(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }

    private Product find(Long id) throws ChangeSetPersister.NotFoundException {
        return products.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

}
