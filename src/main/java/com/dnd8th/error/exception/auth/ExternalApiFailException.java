package com.dnd8th.error.exception.auth;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class ExternalApiFailException extends AccessDeniedException {

    public ExternalApiFailException() {
        super(ErrorCode.EXTERNAL_API_FAILED);
    }
}
