package com.samsungnomads.wheretogo.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API 에러 응답 DTO
 * 📄 클라이언트에게 전달할 에러 정보 구조
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now(); // ⏱️ 에러 발생 시간
    private String code; // 🔢 에러 코드 
    private String message; // 📝 에러 메시지
    private int status; // 🔍 HTTP 상태 코드
    private List<FieldError> errors; // 🧾 필드 에러 목록

    /**
     * 기본 에러 응답 생성
     * 🏭 에러 코드 기반으로 응답 생성
     */
    private ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value();
        this.code = errorCode.getCode();
        this.errors = new ArrayList<>();
    }

    /**
     * 필드 에러가 있는 응답 생성
     * 📋 유효성 검사 오류 등의 필드 오류 포함
     */
    private ErrorResponse(final ErrorCode errorCode, final List<FieldError> errors) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value();
        this.code = errorCode.getCode();
        this.errors = errors;
    }

    /**
     * ErrorCode만 있는 에러의 응답 생성
     * 🛠️ 비즈니스 예외 등의 응답 생성
     */
    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    /**
     * BindingResult 에러 응답 생성
     * 🧩 폼 유효성 검사 오류 응답 생성
     */
    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    /**
     * 타입 불일치 에러 응답 생성
     * 🔄 타입 변환 실패 오류 응답
     */
    public static ErrorResponse of(final ErrorCode errorCode, final MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(errorCode, errors);
    }

    /**
     * 필드 에러 DTO
     * 📄 특정 필드의 에러 정보 표현
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field; // 🔤 필드명
        private String value; // 💯 입력값
        private String reason; // ❓ 오류 이유

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
} 