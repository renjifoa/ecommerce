package com.onebox.ecommerce.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST controllers.
 * This class provides a centralized mechanism for handling exceptions thrown
 * by the controllers in the e-commerce application. It specifically handles
 * IllegalArgumentException and returns a structured error response with a
 * NOT_FOUND (404) HTTP status.
 */
@RestControllerAdvice
public class WebRestControllerAdvice {

    /**
     * Handles IllegalArgumentException and returns an error response when the cartId doesn't exist.
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity containing an ErrorResponse with the exception message and a 404 status code
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Represents an error response returned by the API when an exception occurs.
     */
    @Getter
    @Setter
    public static class ErrorResponse {
        /**
         * The error message to be returned in the response.
         */
        private String error;

        /**
         * Constructs an ErrorResponse with the specified error message.
         *
         * @param error the error message
         */
        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
