package com.subhash.ims.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<>();

    // FIXED: Removed CascadeType.REMOVE danger
    @OneToMany(mappedBy = "category",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products = new ArrayList<>();

    // =========================
    // HIERARCHY MANAGEMENT
    // =========================

    public void addSubCategory(Category subCategory) {
        if (subCategory == null) return;

        subCategories.add(subCategory);
        subCategory.setParentCategory(this);
    }

    public void removeSubCategory(Category subCategory) {
        if (subCategory == null) return;

        subCategories.remove(subCategory);
        subCategory.setParentCategory(null);
    }

    // =========================
    // PRODUCT MANAGEMENT
    // =========================

    protected void addProduct(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
            product.setCategory(this);
        }
    }

    protected void removeProduct(Product product) {
        if (product != null) {
            products.remove(product);
            product.setCategory(null);
        }
    }
}