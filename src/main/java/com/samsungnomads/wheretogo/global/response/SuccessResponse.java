package com.samsungnomads.wheretogo.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class SuccessResponse<T> {

    private final int status;
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
