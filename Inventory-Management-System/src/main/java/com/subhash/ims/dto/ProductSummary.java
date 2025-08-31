package com.subhash.ims.dto;

import com.subhash.ims.model.QuantityType;

public record ProductSummary(
    Long id,
    String name,
    String sku,
    Integer priceCents,
    QuantityType quantityType) {
}
