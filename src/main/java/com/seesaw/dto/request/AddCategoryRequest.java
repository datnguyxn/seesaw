package com.seesaw.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryRequest {
    private String name;
    private String description;
    private String categoryId;
    private String categoryName;
    private String categoryDescription;
}
