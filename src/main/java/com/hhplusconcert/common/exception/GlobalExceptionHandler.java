package com.hhplusconcert.common.exception;


import com.hhplusconcert.common.exception.model.CustomGlobalException;
import com.hhplusconcert.common.exception.model.vo.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomGlobalException.class)
    public ResponseEntity<String> handleRuntimeException(CustomGlobalException ex) {
        ErrorType errorType = ex.getErrorType();
        log.error(ex.getErrorType().getReasonPhrase(), ex);
        return ResponseEntity.status(errorType.getValue()).body(errorType.getReasonPhrase());
    }
}
