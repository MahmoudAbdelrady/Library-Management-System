package com.maidscc.librarymanagementsystem.util;

import com.maidscc.librarymanagementsystem.util.Response.ResponseMaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof HttpRequestMethodNotSupportedException reqEx) {
            httpStatus = (HttpStatus) reqEx.getStatusCode();
        }
        return ResponseEntity.status(httpStatus).body(ResponseMaker.failRes(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMismatchException(MethodArgumentTypeMismatchException ex) {
        String attributeName = ex.getName();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMaker.failRes(attributeName + " not found"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String fieldError = error.getDefaultMessage();
            errors.put(fieldName, fieldError);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMaker.failRes("Validation Error", errors));
    }
}
