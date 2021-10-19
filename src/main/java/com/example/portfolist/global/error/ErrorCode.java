package com.example.portfolist.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    WRONG_FILE(400, "Wrong File"),
    INVALID_TOKEN(401, "Invalid Token"),
    PASSWORD_NOT_MATCHED(401, "Password Not Matched"),
    PERMISSION_DENIED(403, "Permission Denied"),

    USER_NOT_FOUND(404, "User Not Found"),
    NOT_NORMAL_USER(404, "Not Normal User"),
    USER_DUPLICATED(409, "User Duplicated"),
    EMAIL_NOT_AUTHORIZED(401, "Email Not Authorized"),
    FIELD_NOT_FOUND(404, "Field Not Found"),

    PORTFOLIO_NOT_FOUND(404, "Portfolio Not Found"),
    COMMENT_NOT_FOUND(404, "Comment Not Found");

    private final int status;
    private final String message;
}
