package com.samsungnomads.wheretogo.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class SuccessResponse<T> {

    @Schema(description = "상태 코드", example = "201")
    private final int status;

    @Schema(description = "상태 메시지", example = "요청이 성공했습니다.")
    private final String message;
    private T data;

    public static ResponseEntity<SuccessResponse> of(SuccessCode successCode) {
        return ResponseEntity
                .status(successCode.getStatus())
                .body(new SuccessResponse(successCode.getStatusCode(), successCode.getMessage(), null));
    }

    public static <T> ResponseEntity<SuccessResponse<T>> of(SuccessCode successCode, T data) {
        return ResponseEntity
                .status(successCode.getStatus())
                .body(new SuccessResponse<T>(successCode.getStatusCode(), successCode.getMessage(), data));
    }

}
