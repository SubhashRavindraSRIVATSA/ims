package com.subhash.ims.dto;

import com.subhash.ims.model.QuantityType;

import java.time.Instant;

public record ProductResponse(
    Long id,
    String name,
    String sku,
    Integer price,
    QuantityType quantityType,
    Long categoryId,
    String categoryName,
    Instant createdAt,
    Instant updatedAt) {

//    public ProductResponse(Long id, String name, String sku, Integer price, QuantityType quantityType, Long id1, String name1, Instant createdAt, Instant updatedAt) {
//    }
}
