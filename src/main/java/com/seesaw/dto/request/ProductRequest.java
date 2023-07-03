package com.seesaw.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty(message = "Name is required")
    private String name;
    private String brand;
    private String description;
    private Float price;
    private int quantity;
    private String image_path;
    @NotEmpty
    private String collection_id;
    @NotEmpty
    private String category_id;
}
