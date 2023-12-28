package com.example.client.exceptions.validation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldError {

    private String name;
    private Object rejectValue;
    private String message;

    public FieldError(String name, Object rejectValue, String message) {
        this.name = name;
        this.rejectValue = rejectValue;
        this.message = message;
    }

}