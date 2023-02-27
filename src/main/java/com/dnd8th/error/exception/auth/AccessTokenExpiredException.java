package com.dnd8th.error.exception.auth;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class AccessTokenExpiredException extends AccessDeniedException {

    public AccessTokenExpiredException() {
        super(ErrorCode.ACCESS_TOKEN_EXPIRED);
    }
}
