package com.matheusgondra.booksapi.infrastructure.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ResponseError {
  @JsonProperty("status_code")
  private int statusCode;

  private String error;

  public ResponseError(int statusCode, String error) {
    this.statusCode = statusCode;
    this.error = error;
  }

  public ResponseError(HttpStatus statusCode, String error) {
    this.statusCode = statusCode.value();
    this.error = error;
  }

  public ResponseError(HttpStatus statusCode, String field, String error) {
    this.statusCode = statusCode.value();
    this.error = field + " " + error;
  }

  public String getError() {
    return error;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
