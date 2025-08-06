package com.matheusgondra.booksapi.infrastructure.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.matheusgondra.booksapi.infrastructure.error.ResponseError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class BadRequestControllerAdvice {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseError> handle(MethodArgumentNotValidException ex) {

    FieldError fieldError = ex.getBindingResult().getFieldError();

    PropertyNamingStrategy namingStrategy = PropertyNamingStrategies.SNAKE_CASE;
    String fieldName = namingStrategy.nameForField(null, null, fieldError.getField());
    String errorMessage = fieldError.getDefaultMessage();

    ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST, fieldName, errorMessage);
    return ResponseEntity.badRequest().body(error);
  }
}
