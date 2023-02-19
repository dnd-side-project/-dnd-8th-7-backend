package com.dnd8th.error.exception.auth;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class AccessTokenNotFoundException extends AccessDeniedException {

    public AccessTokenNotFoundException() {
        super(ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_HEADER);
    }
}
