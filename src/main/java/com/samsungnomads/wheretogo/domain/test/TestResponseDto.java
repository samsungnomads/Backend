package com.samsungnomads.wheretogo.domain.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestResponseDto {

    @Schema(description = "테스트 메시지", example = "테스트 메시지 입니다")
    private String message;
    @Schema(description = "숫자 값", example = "123")
    private Integer number;
}
