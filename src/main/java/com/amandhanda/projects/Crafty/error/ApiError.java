package com.amandhanda.projects.Crafty.error;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiError(HttpStatus status, String message,Instant timestamp, @JsonInclude(JsonInclude.Include.NON_NULL) List<ApiFieldError> errors) {
      public ApiError(HttpStatus status, String message) {
        this(status, message, Instant.now(),null);
      }
 
      public ApiError(HttpStatus status, String message, List<ApiFieldError> fieldErrors) {
        this(status, message, Instant.now(), fieldErrors);
      }
}

record ApiFieldError(String field, String message){}
