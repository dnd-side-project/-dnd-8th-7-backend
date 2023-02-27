package com.dnd8th.error.exception.review;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class ReviewAccessDeniedException extends AccessDeniedException {
    public ReviewAccessDeniedException() {
        super(ErrorCode.REVIEW_ACCESS_DENIED);
    }
}
