package com.dnd8th.error.exception.auth;

import com.dnd8th.error.exception.AccessDeniedException;
import com.dnd8th.error.exception.ErrorCode;

public class AccessTokenNotFoundException extends AccessDeniedException {

<<<<<<< HEAD:src/main/java/com/dnd8th/error/exception/user/AccessTokenNotFoundException.java
    public AccessTokenNotFoundException(ErrorCode errorCode){
        super(errorCode);
=======
    public AccessTokenNotFoundException() {
        super(ErrorCode.ACCESS_TOKEN_NOT_FOUND_IN_HEADER);
>>>>>>> main:src/main/java/com/dnd8th/error/exception/auth/AccessTokenNotFoundException.java
    }
}
