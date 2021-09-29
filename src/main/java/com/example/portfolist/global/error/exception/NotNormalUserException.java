package com.example.portfolist.global.error.exception;

import com.example.portfolist.global.error.ErrorCode;

public class NotNormalUserException extends GlobalException {

    public NotNormalUserException() {
        super(ErrorCode.NOT_NORMAL_USER);
    }
}
