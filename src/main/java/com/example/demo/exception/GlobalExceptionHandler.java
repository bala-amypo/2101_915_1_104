package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "NOT_FOUND",
                        ex.getMessage(),
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "UNAUTHORIZED",
                        ex.getMessage(),
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message =
                ex.getBindingResult()
                  .getFieldErrors()
                  .get(0)
                  .getDefaultMessage();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "VALIDATION_ERROR",
                        message,
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraint(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "CONSTRAINT_VIOLATION",
                        ex.getMessage(),
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "INTERNAL_SERVER_ERROR",
                        "An unexpected error occurred",
                        request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
