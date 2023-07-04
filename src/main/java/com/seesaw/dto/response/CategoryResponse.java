package com.seesaw.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    private List<ProductResponse> products;
}
