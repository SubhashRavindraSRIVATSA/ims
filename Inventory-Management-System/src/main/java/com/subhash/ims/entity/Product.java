package com.subhash.ims.entity;

import com.subhash.ims.enums.Units;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Product price must be a positive value")
    private BigDecimal price;

    // FIXED: Use BigDecimal instead of Double
    @Positive(message = "Stock must be positive")
    private BigDecimal stockQuantity;

    // NEW: Unit support (critical for real systems)
    @Enumerated(EnumType.STRING)
    private Units unit;

    private String description;

    private LocalDateTime expiryDate;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // =========================
    // AUDIT
    // =========================

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // =========================
    // SAFE toString (NO recursion)
    // =========================

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", unit=" + unit +
                ", categoryId=" + (category != null ? category.getCategoryId() : null) +
                '}';
    }
}