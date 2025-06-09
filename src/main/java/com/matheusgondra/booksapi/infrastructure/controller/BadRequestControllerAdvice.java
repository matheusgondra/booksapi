package com.matheusgondra.booksapi.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.matheusgondra.booksapi.infrastructure.error.ResponseError;

@RestControllerAdvice
public class BadRequestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handler(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity.badRequest().body(error);
    }
}
