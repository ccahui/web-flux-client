package com.example.client.exceptions;


import com.example.client.dtos.ApiBadRequestResponse;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String DESCRIPTION = "Bad Request(400)";

    private ApiBadRequestResponse response;
    public BadRequestException(ApiBadRequestResponse response) {
        super(DESCRIPTION);
        this.response = response;
    }

}