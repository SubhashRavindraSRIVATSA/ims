package com.subhash.ims.service.impl;

import com.subhash.ims.Repository.CategoryRepository;
import com.subhash.ims.Repository.ProductRepository;
import com.subhash.ims.dto.CategoryCreateRequest;
import com.subhash.ims.dto.CategoryResponse;
import com.subhash.ims.dto.ProductResponse;
import com.subhash.ims.dto.ProductSummary;
import com.subhash.ims.model.Category;
import com.subhash.ims.model.Product;
import com.subhash.ims.service.CategoryService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categories;

    @Autowired
    ProductRepository products;

    @Override
    public Page<CategoryResponse> list(Pageable pageable) {
        return categories.findAll(pageable).map(this::toResponse);
    }

    @Override
    public CategoryResponse create(CategoryCreateRequest req) throws BadRequestException {
        if (categories.existsByNameIgnoreCase(req.name())) {
            throw new BadRequestException("Category name already exists");
        }
        Category c = new Category();
        c.setName(req.name());
        c.setDescription(req.description());
        categories.save(c);
        return toResponse(c);
    }

    @Override
    public CategoryResponse get(Long id) throws ChangeSetPersister.NotFoundException {
        return toResponse(find(id));
    }

    @Override
    public CategoryResponse getWithProducts(Long id) throws ChangeSetPersister.NotFoundException {
        Category c = find(id);
        List<ProductSummary> prodSummaries = products.findAllByCategory_Id(id).stream()
                .map(this::toSummary)
                .toList();
        return new CategoryResponse(
                c.getId(), c.getName(), c.getDescription(),
                c.getCreatedAt(), c.getUpdatedAt(),
                prodSummaries
        );
    }

    @Override
    public CategoryResponse update(Long id, CategoryCreateRequest req) throws BadRequestException, ChangeSetPersister.NotFoundException {
        Category c = find(id);
        boolean nameChanged = !c.getName().equalsIgnoreCase(req.name());
        if (nameChanged && categories.existsByNameIgnoreCase(req.name())) {
            throw new BadRequestException("Category name already exists");
        }
        c.setName(req.name());
        c.setDescription(req.description());
        categories.save(c);
        return toResponse(c);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (products.existsByCategory_Id(id)) {
            throw new Exception("Category has products; reassign or delete them first.");
        }
        categories.deleteById(id);
    }

    @Override
    public Page<ProductResponse> listProducts(Long categoryId, Pageable pageable) throws ChangeSetPersister.NotFoundException {
        // ensure category exists
        find(categoryId);
        return products.findAllByCategory_Id(categoryId, pageable).map((java.util.function.Function<? super Product, ? extends ProductResponse>) this::toResponse);
    }

    private CategoryResponse toResponse(Category c) {
        return new CategoryResponse(
                c.getId(), c.getName(), c.getDescription(),
                c.getCreatedAt(), c.getUpdatedAt(),
                null // not embedding products here
        );
    }

    private Category find(Long id) throws ChangeSetPersister.NotFoundException {
        return categories.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    private ProductSummary toSummary(Product p) {
        return new ProductSummary(p.getId(), p.getName(), p.getSku(), p.getPrice(), p.getQuantityType());
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(), p.getName(), p.getSku(), p.getPrice(),
                p.getQuantityType(), p.getCategory().getId(), p.getCategory().getName(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
