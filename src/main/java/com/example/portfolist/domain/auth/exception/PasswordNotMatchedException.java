package com.example.portfolist.domain.auth.exception;

import com.example.portfolist.global.error.ErrorCode;
import com.example.portfolist.global.error.exception.GlobalException;

public class PasswordNotMatchedException extends GlobalException {

    public PasswordNotMatchedException() {
        super(ErrorCode.PASSWORD_NOT_MATCHED);
    }

}
