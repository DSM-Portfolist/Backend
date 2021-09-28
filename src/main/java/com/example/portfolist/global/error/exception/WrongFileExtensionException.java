package com.example.portfolist.global.error.exception;

import com.example.portfolist.global.error.ErrorCode;

public class WrongFileExtensionException extends GlobalException {

    public WrongFileExtensionException() {
        super(ErrorCode.WRONG_FILE_EXTENSION);
    }

}
