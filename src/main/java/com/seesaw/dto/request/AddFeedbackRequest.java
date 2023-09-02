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
public class AddFeedbackRequest {
    private String note;
    private int rating;
    private String product_id;
    private String user_id;
}
