package com.kcischan.kcischan;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> handleMyCustomException(
      ResponseStatusException ex,
      HttpServletRequest request) {
    Map<String, String> body = new HashMap<>();
    body.put("message", ex.getReason());
    body.put("status", String.valueOf(ex.getStatusCode().value()));
    body.put("error", ex.getLocalizedMessage());
    body.put("timestamp", String.valueOf(System.currentTimeMillis()));
    body.put("path", request.getRequestURI());
    return new ResponseEntity<>(body, ex.getStatusCode());
  }
}
