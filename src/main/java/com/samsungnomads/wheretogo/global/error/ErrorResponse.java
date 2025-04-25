package com.samsungnomads.wheretogo.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * 에러 응답 클래스
 * 🚨 API 에러 응답에 대한 표준 포맷 제공
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "에러 응답 공통 형식")
public class ErrorResponse {

    @Schema(description = "응답 코드", example = "400")
    private int status;
    
    @Schema(description = "에러 코드", example = "C001")
    private String code;
    
    @Schema(description = "에러 메시지", example = "잘못된 입력값입니다")
    private String message;
    
    @Schema(description = "상세 에러 정보")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> errors;

    /**
     * 에러 응답 생성 (ErrorCode 기반)
     * 🔍 ErrorCode를 기반으로 에러 응답 생성
     */
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 에러 응답 생성 (ErrorCode, 메시지 기반)
     * 🔍 ErrorCode와 사용자 정의 메시지 기반으로 에러 응답 생성
     */
    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getCode(), message);
    }

    /**
     * 입력 값 검증 에러 응답 생성
     * 🔍 BindingResult에서 발생한 검증 오류를 포함한 에러 응답 생성
     */
    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getCode(), errorCode.getMessage(), FieldError.of(bindingResult));
    }

    /**
     * 타입 불일치 에러 응답 생성
     * 🔍 MethodArgumentTypeMismatchException에서 발생한 오류를 포함한 에러 응답 생성
     */
    public static ErrorResponse of(ErrorCode errorCode, MethodArgumentTypeMismatchException e) {
        String value = e.getValue() == null ? "" : e.getValue().toString();
        List<FieldError> errors = List.of(FieldError.of(e.getName(), value, e.getErrorCode()));
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getCode(), errorCode.getMessage(), errors);
    }

    private ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(int status, String code, String message, List<FieldError> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    /**
     * 필드 에러 정보
     * 🚨 각 필드의 유효성 검증 오류 정보
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "필드 에러 정보")
    public static class FieldError {
        @Schema(description = "오류가 발생한 필드명", example = "email")
        private String field;
        
        @Schema(description = "오류가 발생한 값", example = "invalid-email")
        private String value;
        
        @Schema(description = "오류 메시지", example = "유효한 이메일 형식이 아닙니다")
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        /**
         * BindingResult로부터 필드 에러 목록 생성
         * 🔍 입력 값 검증에서 발생한 모든 필드 에러 정보 추출
         */
        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

        /**
         * 단일 필드 에러 생성
         * 🔍 단일 필드에 대한 에러 정보 생성
         */
        public static FieldError of(String field, String value, String reason) {
            return new FieldError(field, value, reason);
        }
    }
} 