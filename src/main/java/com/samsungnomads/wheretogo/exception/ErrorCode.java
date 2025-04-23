package com.samsungnomads.wheretogo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 애플리케이션에서 사용하는 에러 코드 열거형
 * 🚨 에러 코드와 메시지, HTTP 상태 코드를 관리
 */
@Getter
public enum ErrorCode {
    
    // 공통 오류
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "지원하지 않는 HTTP 메서드입니다"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "요청한 리소스를 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "서버 내부 오류가 발생했습니다"),
    
    // 회원 관련 오류
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "M001", "이미 사용 중인 이메일입니다"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M002", "회원을 찾을 수 없습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "M003", "비밀번호가 일치하지 않습니다"),
    
    // 필터 관련 오류
    FILTER_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "필터를 찾을 수 없습니다"),
    
    // 지하철역 관련 오류
    STATION_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "지하철역을 찾을 수 없습니다");
    
    private final HttpStatus status;
    private final String code;
    private final String message;
    
    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
} 