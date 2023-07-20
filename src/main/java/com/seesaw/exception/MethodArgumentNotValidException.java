package com.seesaw.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class MethodArgumentNotValidException extends BindException {
    public MethodArgumentNotValidException(BindingResult message) {
        super(message);
    }
}
