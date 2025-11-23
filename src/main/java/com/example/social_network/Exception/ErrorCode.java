package com.example.social_network.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001,"User existed", HttpStatus.BAD_REQUEST),
    USER_DON_EXIST(1002,"User don't exist", HttpStatus.BAD_REQUEST),
    POST_DON_EXIST(1003,"Post don't exist", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1006, "User Not Found", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1004, "You do not have permission", HttpStatus.FORBIDDEN),
    COMMENT_NOT_FOUND(1007, "Comment not found",HttpStatus.NOT_FOUND),
    COMMENT_DONT_EXIST(1008, "Comment dont exist",HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

}
