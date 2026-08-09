package com.subhash.ims.repository;

import com.subhash.ims.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findProductsByCategoryId(
            @Param("categoryId") Long categoryId
    );
//    // Find all products in a category
//    List<Product> findByCategory(Category category);
//
//    // Find products by category ID (more efficient sometimes)
//    List<Product> findByCategory_CategoryId(Long categoryId);
//
//    // Search by product name
//    List<Product> findByNameContainingIgnoreCase(String name);
//
//    // Filter by unit type
//    List<Product> findByUnit(UnitType units);
//
//    // Example: low stock query
//    List<Product> findByQuantityLessThan(double threshold);
}
