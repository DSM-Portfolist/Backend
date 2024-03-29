package com.example.portfolist.global.error.exception;

import com.example.portfolist.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalException (ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GlobalException (String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
