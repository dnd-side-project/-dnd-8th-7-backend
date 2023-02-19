package com.dnd8th.error.exception;

public class FormattingException extends BusinessException {
    public FormattingException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FormattingException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
