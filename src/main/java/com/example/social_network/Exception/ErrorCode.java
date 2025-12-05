package com.example.social_network.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(1001,"User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),

    POST_NOT_FOUND(1003,"Post not found", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(1004, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "You do not have permission", HttpStatus.FORBIDDEN),

    COMMENT_NOT_FOUND(1006, "Comment not found", HttpStatus.NOT_FOUND),

    INVALID_EXPRESSION(1007, "Invalid expression", HttpStatus.BAD_REQUEST),

    EXPRESSION_TYPE_NOT_FOUND(1008, "Expression type not found", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

}
