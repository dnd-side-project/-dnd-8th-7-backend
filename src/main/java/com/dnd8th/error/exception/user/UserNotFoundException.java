package com.dnd8th.error.exception.user;

import com.dnd8th.error.exception.EntityNotFoundException;
import com.dnd8th.error.exception.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
