package com.matheusgondra.booksapi.infrastructure.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.matheusgondra.booksapi.domain.exception.InvalidCredentialsException;
import com.matheusgondra.booksapi.infrastructure.error.ResponseError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class UnauthorizedControllerAdvice {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseError> handle(InvalidCredentialsException ex) {
        ResponseError error = new ResponseError(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
