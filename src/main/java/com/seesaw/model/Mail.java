package com.seesaw.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Mail {
    private String to;
    private String subject;
    private String content;

}
