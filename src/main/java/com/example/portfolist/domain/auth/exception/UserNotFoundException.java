package com.example.portfolist.domain.auth.exception;

import com.example.portfolist.global.error.ErrorCode;
import com.example.portfolist.global.error.exception.GlobalException;

public class UserNotFoundException extends GlobalException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

}
