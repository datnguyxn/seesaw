package com.seesaw.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String brand;
    private String description;
    private Double price;
    private int quantity;
    private String image_path;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private LocalDate createdAt;
}
