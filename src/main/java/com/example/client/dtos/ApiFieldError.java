package com.example.client.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiFieldError {

    private String name;
    private String rejectValue;
    private String message;

    public ApiFieldError(String name, String rejectValue, String message) {
        this.name = name;
        this.rejectValue = rejectValue;
        this.message = message;
    }

}