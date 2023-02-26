package com.dnd8th.error.exception.review;

import com.dnd8th.error.exception.EntityNotFoundException;
import com.dnd8th.error.exception.ErrorCode;

public class ReviewNotFoundException extends EntityNotFoundException {
    public ReviewNotFoundException() {
        super(ErrorCode.REVIEW_NOT_FOUND);
    }
}
