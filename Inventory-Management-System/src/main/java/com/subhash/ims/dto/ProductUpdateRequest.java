package com.subhash.ims.dto;

import com.subhash.ims.model.QuantityType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(
        @NotBlank @Size(max = 150) String name,
        @NotNull @Min(0) Integer price,
        @NotNull QuantityType quantityType,   // <-- added
        @NotNull Long categoryId) {
}
