package com.subhash.ims.dto;

import java.time.Instant;
import java.util.List;

public record CategoryResponse(
    Long id,
    String name,
    String description,
    Instant createdAt,
    Instant updatedAt,
    List<ProductSummary> products) {
}
