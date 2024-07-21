package com.springvoyage.prod.advices;

import com.springvoyage.prod.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(){
                ApiError apiError = new ApiError(
                        "This post was not found",
                        HttpStatus.NOT_FOUND
                );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
