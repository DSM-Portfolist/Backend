package com.example.portfolist.domain.auth.exception;

import com.example.portfolist.global.error.ErrorCode;
import com.example.portfolist.global.error.exception.GlobalException;

public class UserDuplicatedException extends GlobalException {

    public UserDuplicatedException() {
        super(ErrorCode.USER_DUPLICATED);
    }

}
