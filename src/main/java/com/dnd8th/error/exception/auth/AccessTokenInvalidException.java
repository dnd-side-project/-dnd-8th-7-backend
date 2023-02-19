package com.dnd8th.error.exception.auth;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class AccessTokenInvalidException extends AccessDeniedException {

    public AccessTokenInvalidException() {
        super(ErrorCode.ACCESS_TOKEN_INVALID);
    }
}
