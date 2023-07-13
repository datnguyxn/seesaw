package com.seesaw.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seesaw.model.FeedbackKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private FeedbackKey id;
    @JsonProperty("user_name")
    private String user;
    @JsonProperty("product_name")
    private String product;
    private String note;
    private int rating;
}
