package com.seesaw.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String id;
    @NotEmpty(message = "Name is required")
    private String name;
    private String brand;
    private String description;
    private Double price;
    private int quantity;
    private MultipartFile image_path;
    private String collection_id;
    private String category_id;
}
