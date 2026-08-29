package com.cyaneer.gamesdb_api.common;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ) {
        List<String> details = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(this::formatFieldError)
            .collect(Collectors.toList());
        
        ApiErrorResponse response = new ApiErrorResponse(
            Instant.now(), 
            HttpStatus.BAD_REQUEST.value(), 
            "Bad Request", 
            "Validation failed", 
            request.getRequestURI(), 
            details
        );

        return ResponseEntity.badRequest().body(response);
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
        ResourceNotFoundException e,
        HttpServletRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
            Instant.now(), 
            HttpStatus.NOT_FOUND.value(), 
            "Not Found", 
            e.getMessage(), 
            request.getRequestURI(), 
            List.of(e.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<ApiErrorResponse> handleInUse(
        ResourceInUseException e,
        HttpServletRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
            Instant.now(), 
            HttpStatus.CONFLICT.value(), 
            "Conflict", 
            e.getMessage(), 
            request.getRequestURI(), 
            List.of(e.getMessage())
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
