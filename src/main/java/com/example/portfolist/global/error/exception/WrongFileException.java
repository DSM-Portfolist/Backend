package com.example.portfolist.global.error.exception;

import com.example.portfolist.global.error.ErrorCode;

public class WrongFileException extends GlobalException {

    public WrongFileException() {
        super(ErrorCode.WRONG_FILE);
    }

}
