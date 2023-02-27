package com.dnd8th.error.exception.user;

import com.dnd8th.error.exception.BusinessException;
import com.dnd8th.error.exception.ErrorCode;

public class ImageUploadFailedException extends BusinessException {

    public ImageUploadFailedException() {
        super(ErrorCode.IMAGE_UPLOADED_FAILED);
    }
}
