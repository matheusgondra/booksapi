package com.matheusgondra.booksapi.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.matheusgondra.booksapi.infrastructure.error.ResponseError;

@RestControllerAdvice
public class InternalServerErrorControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handler(RuntimeException ex) {
        ResponseError error = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
}
