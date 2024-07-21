package com.springvoyage.prod.advices;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private LocalDateTime happenedAt;
    private HttpStatus httpStatus;

    public ApiError() {
        this.happenedAt = LocalDateTime.now();
    }

    public ApiError(String message, HttpStatus httpStatus) {
        this();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
