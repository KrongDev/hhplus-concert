package com.hhplusconcert.interfaces.exception;


import com.hhplusconcert.domain.common.exception.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.ErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomGlobalException.class)
    public ResponseEntity<String> handleRuntimeException(CustomGlobalException ex) {
        ErrorType errorType = ex.getErrorType();
        return ResponseEntity.status(errorType.getValue()).body(errorType.getReasonPhrase());
    }
}
