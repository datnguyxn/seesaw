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
    @NotEmpty
    private String product_id;
    @NotEmpty
    private String user_id;
}
