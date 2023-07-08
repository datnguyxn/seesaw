package com.seesaw.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
