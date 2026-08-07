package com.subhash.ims.repository;

import com.subhash.ims.entity.Category;
import com.subhash.ims.entity.Product;
import com.subhash.ims.enums.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find all products in a category
    List<Product> findByCategory(Category category);

    // Find products by category ID (more efficient sometimes)
    List<Product> findByCategory_CategoryId(Long categoryId);

    // Search by product name
    List<Product> findByNameContainingIgnoreCase(String name);

    // Filter by unit type
    List<Product> findByUnit(Units units);

    // Example: low stock query
    List<Product> findByQuantityLessThan(double threshold);
}
