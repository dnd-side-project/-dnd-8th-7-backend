package com.dnd8th.error.exception.user;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class AccessTokenNotFoundException extends AccessDeniedException {

    public AccessTokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
