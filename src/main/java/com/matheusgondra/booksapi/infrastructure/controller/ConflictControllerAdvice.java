package com.matheusgondra.booksapi.infrastructure.controller;

import com.matheusgondra.booksapi.domain.exception.UserAlreadyExistsException;
import com.matheusgondra.booksapi.infrastructure.error.ResponseError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ConflictControllerAdvice {
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ResponseError> handleUserAlreadyExists(UserAlreadyExistsException ex) {
    ResponseError error = new ResponseError(HttpStatus.CONFLICT, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
