package com.samsungnomads.wheretogo.global.error;

import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * GlobalExceptionHandler 클래스 테스트
 * 🚦 전역 예외 처리기의 다양한 예외 처리 기능 검증
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("비즈니스 예외가 발생하면 해당 에러코드에 맞는 응답이 반환되어야 한다")
    void handleBusinessException() {
        // given
        BusinessException exception = new BusinessException(ErrorCode.ENTITY_NOT_FOUND);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(exception);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(ErrorCode.ENTITY_NOT_FOUND.getCode(), errorResponse.getCode());
        assertEquals(ErrorCode.ENTITY_NOT_FOUND.getMessage(), errorResponse.getMessage());
        assertEquals(ErrorCode.ENTITY_NOT_FOUND.getStatus().value(), errorResponse.getStatus());
    }

    @Test
    @DisplayName("입력 값 검증 예외가 발생하면 필드 오류 정보가 포함된 응답이 반환되어야 한다")
    void handleMethodArgumentNotValidException() {
        // given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("memberDto", "email", "test@",
                false, null, null, "이메일 형식이 올바르지 않습니다");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        when(exception.getBindingResult()).thenReturn(bindingResult);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentNotValidException(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(ErrorCode.INVALID_INPUT_VALUE.getCode(), errorResponse.getCode());
        assertEquals(1, errorResponse.getErrors().size());
        assertEquals("email", errorResponse.getErrors().getFirst().getField());
        assertEquals("test@", errorResponse.getErrors().getFirst().getValue());
        assertEquals("이메일 형식이 올바르지 않습니다", errorResponse.getErrors().getFirst().getReason());
    }

    @Test
    @DisplayName("바인딩 예외가 발생하면 필드 오류 정보가 포함된 응답이 반환되어야 한다")
    void handleBindException() {
        // given
        BindException exception = mock(BindException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("memberDto", "age", "abc",
                false, null, null, "숫자 형식이어야 합니다");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        when(exception.getBindingResult()).thenReturn(bindingResult);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleBindException(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(ErrorCode.INVALID_INPUT_VALUE.getCode(), errorResponse.getCode());
        assertEquals(1, errorResponse.getErrors().size());
        assertEquals("age", errorResponse.getErrors().getFirst().getField());
        assertEquals("abc", errorResponse.getErrors().getFirst().getValue());
        assertEquals("숫자 형식이어야 합니다", errorResponse.getErrors().getFirst().getReason());
    }

    @Test
    @DisplayName("지원하지 않는 HTTP 메서드 요청시 적절한 오류 응답이 반환되어야 한다")
    void handleHttpRequestMethodNotSupportedException() {
        // given
        HttpRequestMethodNotSupportedException exception = mock(HttpRequestMethodNotSupportedException.class);
        when(exception.getMessage()).thenReturn("Request method 'POST' not supported");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleHttpRequestMethodNotSupportedException(exception);

        // then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(ErrorCode.METHOD_NOT_ALLOWED.getCode(), errorResponse.getCode());
        assertEquals(ErrorCode.METHOD_NOT_ALLOWED.getMessage(), errorResponse.getMessage());
    }

    @Test
    @DisplayName("메서드 인자 타입 불일치 예외시 적절한 오류 응답이 반환되어야 한다")
    void handleMethodArgumentTypeMismatchException() {
        // given
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("age");
        when(exception.getValue()).thenReturn("abc");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentTypeMismatchException(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(ErrorCode.INVALID_INPUT_VALUE.getCode(), errorResponse.getCode());
        assertEquals(1, errorResponse.getErrors().size());
        assertEquals("age", errorResponse.getErrors().getFirst().getField());
    }

    @Test
    @DisplayName("접근 거부 예외시 적절한 오류 응답이 반환되어야 한다")
    void handleAccessDeniedException() {
        // given
        AccessDeniedException exception = new AccessDeniedException("권한이 없습니다");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleAccessDeniedException(exception);

        // then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), errorResponse.getCode());
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), errorResponse.getMessage());
    }

    @Test
    @DisplayName("예상치 못한 예외시 서버 오류 응답이 반환되어야 한다")
    void handleException() {
        // given
        Exception exception = new Exception("알 수 없는 오류 발생");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), errorResponse.getCode());
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), errorResponse.getMessage());
    }
} 