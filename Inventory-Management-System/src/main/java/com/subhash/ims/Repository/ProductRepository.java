package com.subhash.ims.Repository;

import com.subhash.ims.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    Page<Product> findAllByCategory_Id(Long categoryId, Pageable pageable);
    List<Product> findAllByCategory_Id(Long categoryId); // for embed
    boolean existsByCategory_Id(Long categoryId);
}