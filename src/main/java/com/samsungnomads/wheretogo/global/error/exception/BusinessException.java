package com.samsungnomads.wheretogo.global.error.exception;

import com.samsungnomads.wheretogo.global.error.ErrorCode;
import lombok.Getter;

/**
 * 비즈니스 로직 관련 예외 클래스
 * 🚨 비즈니스 로직 처리 중 발생하는 예외를 표현
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
} 