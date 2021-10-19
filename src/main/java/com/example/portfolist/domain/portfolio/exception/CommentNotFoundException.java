package com.example.portfolist.domain.portfolio.exception;

import com.example.portfolist.global.error.ErrorCode;
import com.example.portfolist.global.error.exception.GlobalException;

public class CommentNotFoundException extends GlobalException {

    public CommentNotFoundException() { super(ErrorCode.COMMENT_NOT_FOUND);}
}
