package com.samsungnomads.wheretogo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ErrorCode 열거형 테스트
 * 🧪 오류 코드의 속성과 동작을 검증
 */
class ErrorCodeTest {

    @Test
    @DisplayName("모든 ErrorCode는 적절한 HTTP 상태 코드를 가진다")
    void errorCodeShouldHaveProperHttpStatus() {
        // 공통 오류 검증
        assertEquals(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT_VALUE.getStatus());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.METHOD_NOT_ALLOWED.getStatus());
        assertEquals(HttpStatus.NOT_FOUND, ErrorCode.ENTITY_NOT_FOUND.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
        
        // 회원 관련 오류 검증
        assertEquals(HttpStatus.CONFLICT, ErrorCode.EMAIL_DUPLICATION.getStatus());
        assertEquals(HttpStatus.NOT_FOUND, ErrorCode.MEMBER_NOT_FOUND.getStatus());
        assertEquals(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_PASSWORD.getStatus());
        
        // 필터 관련 오류 검증
        assertEquals(HttpStatus.NOT_FOUND, ErrorCode.FILTER_NOT_FOUND.getStatus());
        
        // 역 관련 오류 검증
        assertEquals(HttpStatus.NOT_FOUND, ErrorCode.STATION_NOT_FOUND.getStatus());
    }
    
    @Test
    @DisplayName("모든 ErrorCode는 고유한 코드 값을 가진다")
    void errorCodeShouldHaveUniqueCode() {
        // given
        ErrorCode[] errorCodes = ErrorCode.values();
        
        // when & then
        for (int i = 0; i < errorCodes.length; i++) {
            for (int j = i + 1; j < errorCodes.length; j++) {
                assertNotEquals(
                    errorCodes[i].getCode(), 
                    errorCodes[j].getCode(),
                    String.format("%s와 %s의 코드 값이 중복됩니다", errorCodes[i], errorCodes[j])
                );
            }
        }
    }
    
    @Test
    @DisplayName("모든 ErrorCode는 메시지를 가진다")
    void errorCodeShouldHaveMessage() {
        // given
        ErrorCode[] errorCodes = ErrorCode.values();
        
        // when & then
        for (ErrorCode errorCode : errorCodes) {
            assertNotNull(errorCode.getMessage());
            assertFalse(errorCode.getMessage().isEmpty());
        }
    }
} 