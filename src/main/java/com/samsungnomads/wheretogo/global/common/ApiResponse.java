package com.samsungnomads.wheretogo.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 응답 공통 형식
 * 📦 API 응답에 대한 표준 포맷 제공
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "API 응답 공통 형식")
public class ApiResponse<T> {

    @Schema(description = "응답 코드", example = "200")
    private int code;

    @Schema(description = "응답 상태", example = "SUCCESS")
    private String status;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "응답 데이터")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 성공 응답 생성 (데이터 포함)
     * ✅ 성공 응답을 데이터와 함께 생성
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", "요청이 성공적으로 처리되었습니다.", data);
    }

    /**
     * 성공 응답 생성 (상태코드, 데이터 포함)
     * ✅ 지정된 상태 코드로 성공 응답을 생성
     */
    public static <T> ApiResponse<T> success(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), "SUCCESS", "요청이 성공적으로 처리되었습니다.", data);
    }

    /**
     * 성공 응답 생성 (데이터 없음)
     * ✅ 데이터 없이 성공 응답 생성
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", "요청이 성공적으로 처리되었습니다.", null);
    }

    /**
     * 실패 응답 생성
     * ❌ 실패 응답 생성 (에러 코드, 메시지 포함)
     */
    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), "ERROR", message, null);
    }

    /**
     * 생성자
     */
    private ApiResponse(int code, String status, String message, T data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }
} 