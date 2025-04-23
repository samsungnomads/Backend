package com.samsungnomads.wheretogo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BusinessException 클래스 테스트
 * 🧪 비즈니스 예외의 생성과 정보 접근을 검증
 */
class BusinessExceptionTest {

    @Test
    @DisplayName("ErrorCode로 BusinessException을 생성할 수 있다")
    void createBusinessExceptionWithErrorCode() {
        // given
        ErrorCode errorCode = ErrorCode.EMAIL_DUPLICATION;
        
        // when
        BusinessException exception = new BusinessException(errorCode);
        
        // then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
    }
    
    @Test
    @DisplayName("ErrorCode와 사용자 정의 메시지로 BusinessException을 생성할 수 있다")
    void createBusinessExceptionWithCustomMessage() {
        // given
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        String customMessage = "이메일 형식이 올바르지 않습니다: example@test";
        
        // when
        BusinessException exception = new BusinessException(errorCode, customMessage);
        
        // then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(customMessage, exception.getMessage());
    }
    
    @Test
    @DisplayName("ErrorCode와 원인 예외로 BusinessException을 생성할 수 있다")
    void createBusinessExceptionWithCause() {
        // given
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        Throwable cause = new RuntimeException("DB 연결 실패");
        
        // when
        BusinessException exception = new BusinessException(errorCode, cause);
        
        // then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
    
    @Test
    @DisplayName("BusinessException은 RuntimeException을 상속한다")
    void businessExceptionShouldExtendRuntimeException() {
        // given
        BusinessException exception = new BusinessException(ErrorCode.INVALID_PASSWORD);
        
        // when & then
        assertInstanceOf(RuntimeException.class, exception);
    }
} 