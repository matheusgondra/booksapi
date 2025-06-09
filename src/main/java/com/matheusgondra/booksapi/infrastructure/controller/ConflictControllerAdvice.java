package com.matheusgondra.booksapi.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.matheusgondra.booksapi.domain.exception.UserAlreadyExistsException;
import com.matheusgondra.booksapi.infrastructure.error.ResponseError;

@RestControllerAdvice
public class ConflictControllerAdvice {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseError> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ResponseError error = new ResponseError(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
