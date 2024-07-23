package com.springvoyage.mvc_restful_api.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Data
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private List<String> subErrors;
}
