package com.kg.walkbababackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

public class GlobalErrorHandler {

    // TODO: CartExceptionHandler is a terrible class name for this project
    @ControllerAdvice
    public static class CartExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler({NoSuchElementException.class})
        protected ResponseEntity<String> handleChatGptError(NoSuchElementException ex) {
            String errorMessage = ex.getMessage();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }
        @ExceptionHandler({IllegalArgumentException.class})
        protected ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
            String errorMessage = ex.getMessage();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }
    }
}
