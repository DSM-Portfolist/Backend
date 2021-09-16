package com.example.portfolist.global.error.exception;

import com.example.portfolist.global.error.ErrorCode;

public class InvalidTokenException extends GlobalException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
