package com.samsungnomads.wheretogo.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ErrorResponse 클래스 테스트
 * 🧪 오류 응답 DTO의 생성과 정보 접근을 검증
 */
class ErrorResponseTest {
    
    @Test
    @DisplayName("ErrorCode로 기본 ErrorResponse 객체를 생성할 수 있다")
    void createErrorResponseWithErrorCode() {
        // given
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        
        // when
        ErrorResponse response = ErrorResponse.of(errorCode);
        
        // then
        assertNotNull(response);
        assertEquals(errorCode.getCode(), response.getCode());
        assertEquals(errorCode.getMessage(), response.getMessage());
        assertEquals(errorCode.getStatus().value(), response.getStatus());
        assertNotNull(response.getTimestamp());
        assertTrue(response.getErrors().isEmpty());
    }
    
    @Test
    @DisplayName("타임스탬프는 현재 시간으로 설정된다")
    void timestampShouldBeCurrentTime() {
        // given
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        
        // when
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
        LocalDateTime after = LocalDateTime.now().plusSeconds(1);
        
        // then
        assertTrue(
            !response.getTimestamp().isBefore(before) && 
            !response.getTimestamp().isAfter(after),
            "타임스탬프가 현재 시간 범위 내에 있어야 합니다"
        );
    }
    
    @Test
    @DisplayName("BindingResult로 필드 오류가 포함된 ErrorResponse를 생성할 수 있다")
    void createErrorResponseWithBindingResult() {
        // given
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        BindingResult bindingResult = mock(BindingResult.class);
        
        // FieldError 객체 생성
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError(
            "memberDto", "email", "test@", 
            false, null, null, "이메일 형식이 올바르지 않습니다"));
        fieldErrors.add(new FieldError(
            "memberDto", "name", "", 
            false, null, null, "이름은 필수 항목입니다"));
        
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
        
        // when
        ErrorResponse response = ErrorResponse.of(errorCode, bindingResult);
        
        // then
        assertNotNull(response);
        assertEquals(errorCode.getCode(), response.getCode());
        assertEquals(errorCode.getMessage(), response.getMessage());
        assertEquals(2, response.getErrors().size());
        
        // 첫 번째 필드 오류 검증
        assertEquals("email", response.getErrors().get(0).getField());
        assertEquals("test@", response.getErrors().get(0).getValue());
        assertEquals("이메일 형식이 올바르지 않습니다", response.getErrors().get(0).getReason());
        
        // 두 번째 필드 오류 검증
        assertEquals("name", response.getErrors().get(1).getField());
        assertEquals("", response.getErrors().get(1).getValue());
        assertEquals("이름은 필수 항목입니다", response.getErrors().get(1).getReason());
    }
    
    @Test
    @DisplayName("MethodArgumentTypeMismatchException으로 ErrorResponse를 생성할 수 있다")
    void createErrorResponseWithTypeMismatchException() {
        // given
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("age");
        when(exception.getValue()).thenReturn("abc");
        when(exception.getErrorCode()).thenReturn("typeMismatch");
        
        // when
        ErrorResponse response = ErrorResponse.of(errorCode, exception);
        
        // then
        assertNotNull(response);
        assertEquals(errorCode.getCode(), response.getCode());
        assertEquals(1, response.getErrors().size());
        
        // 필드 오류 검증
        assertEquals("age", response.getErrors().get(0).getField());
        assertEquals("abc", response.getErrors().get(0).getValue());
        assertEquals("typeMismatch", response.getErrors().get(0).getReason());
    }
} 