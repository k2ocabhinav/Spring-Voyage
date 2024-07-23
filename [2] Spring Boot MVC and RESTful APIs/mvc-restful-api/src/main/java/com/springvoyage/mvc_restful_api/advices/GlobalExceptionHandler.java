package com.springvoyage.mvc_restful_api.advices;

import com.springvoyage.mvc_restful_api.exceptions.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ApiError apiError = ApiError
                .builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(resourceNotFoundException.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception e){
        ApiError apiError = ApiError
                .builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }


// To make the API error response prettier
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiError apiError = ApiError
                .builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Wrong Input")
                .subErrors(errors)
                .build();
        return buildErrorResponseEntity(apiError);
    }
/*
------------------------------------------------------------------------------------------------
HELPER FUNCTIONS
------------------------------------------------------------------------------------------------
*/
    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getHttpStatus());
    }

}





