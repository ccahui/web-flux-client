package com.example.client.exceptions.validation;

import com.example.client.dtos.ApiFieldError;
import com.example.client.exceptions.BadRequestException;
import com.example.client.exceptions.ErrorMessage;
import lombok.Getter;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorMessageFields extends ErrorMessage {
    private static final String DESCRIPTION = "Bad Request, fields Error";

    private List<FieldError> fieldsError = new ArrayList<>();

    public ErrorMessageFields(BadRequestException exception, String path) {

        super(exception.getClass().getSimpleName(), DESCRIPTION, path);
        this.setFieldsError(exception);
    }

    private void setFieldsError(BadRequestException exception) {
        FieldError error;
        for (ApiFieldError fieldError : exception.getResponse().getFieldsError()) {
            error = new FieldError(fieldError.getName(), fieldError.getRejectValue(),
                    fieldError.getMessage());
            fieldsError.add(error);
        }
    }
}