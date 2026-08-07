package com.subhash.ims.repository;

import com.subhash.ims.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Find root categories (no parent)
    List<Category> findByParentCategoryIsNull();

    // Find subcategories of a given category
    List<Category> findByParentCategory(Category parentCategory);

    // Search by name (case insensitive)
    List<Category> findByNameContainingIgnoreCase(String name);
}
