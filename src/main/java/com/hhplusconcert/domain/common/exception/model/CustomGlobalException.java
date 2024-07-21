package com.hhplusconcert.domain.common.exception.model;

import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import lombok.Getter;

@Getter
public class CustomGlobalException extends RuntimeException {
    private final ErrorType errorType;

    public CustomGlobalException(ErrorType errorType) {
        super(errorType.getReasonPhrase());
        this.errorType = errorType;
    }
}
