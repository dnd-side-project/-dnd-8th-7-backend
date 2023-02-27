package com.dnd8th.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE("C01", "Invalid Input Value.", HttpStatus.BAD_REQUEST.value()),
    METHOD_NOT_ALLOWED("C02", "Invalid Method Type.", HttpStatus.METHOD_NOT_ALLOWED.value()),
    ENTITY_NOT_FOUND("C03", "Entity Not Found.", HttpStatus.BAD_REQUEST.value()),
    INTERNAL_SERVER_ERROR("C04", "Server Error.", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    INVALID_TYPE_VALUE("C05", " Invalid Type Value.", HttpStatus.BAD_REQUEST.value()),

    // User
    USER_ACCESS_DENIED("A01", "User Access is Denied.", HttpStatus.UNAUTHORIZED.value()),
    USER_NOT_FOUND("A02", "User is not Found.", HttpStatus.BAD_REQUEST.value()),
    EMAIL_DUPLICATION("A03", "Email is Duplication.", HttpStatus.BAD_REQUEST.value()),

    // User - Token
    ACCESS_TOKEN_EXPIRED("AT01", "Access Token is Expired.", HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_INVALID("AT02", "Access Token is Invalid.", HttpStatus.UNAUTHORIZED.value()),
    ACCESS_TOKEN_NOT_FOUND_IN_HEADER("AT03", "Access Token is not Found in Header.",
            HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_NOT_FOUND("AT04", "Refreshed Token is not Found.",
            HttpStatus.UNAUTHORIZED.value()),
    REFRESH_TOKEN_EXPIRED("AT05", "Refreshed Token is Expired.", HttpStatus.UNAUTHORIZED.value()),

    // Block

    BLOCK_NOT_FOUND("B01", "Block is not Found.", HttpStatus.BAD_REQUEST.value()),
    BLOCK_ACCESS_DENIED("B02", "Block Access is Denied.", HttpStatus.UNAUTHORIZED.value()),

    // Task

    TASK_NOT_FOUND("T01", "Task is not Found.", HttpStatus.BAD_REQUEST.value()),
    TASK_ACCESS_DENIED("T02", "Task Access is Denied.", HttpStatus.UNAUTHORIZED.value()),

    // Review

    REVIEW_NOT_FOUND("R01", "Review is not Found.", HttpStatus.BAD_REQUEST.value()),
    REVIEW_ACCESS_DENIED("R02", "Review Access is Denied.", HttpStatus.UNAUTHORIZED.value()),

    // Keep

    KEEP_NOT_FOUND("K01", "Keep is not Found.", HttpStatus.BAD_REQUEST.value()),
    KEEP_ACCESS_DENIED("K02", "Keep Access is Denied.", HttpStatus.UNAUTHORIZED.value()),

    // external API
    EXTERNAL_API_FAILED("E01", "External API Request is failed.",
            HttpStatus.INTERNAL_SERVER_ERROR.value()),
    IMAGE_UPLOADED_FAILED("E02", "Image Upload API Request is failed.",
            HttpStatus.INTERNAL_SERVER_ERROR.value());

    private final String code;
    private final String message;
    private int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
