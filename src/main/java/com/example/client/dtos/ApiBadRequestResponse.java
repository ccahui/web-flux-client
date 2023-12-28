package com.example.client.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ApiBadRequestResponse {
    private String exception;
    private String message;
    private String path;
    private List<ApiFieldError> fieldsError = new ArrayList<>();
}
