package com.samsungnomads.wheretogo.domain.test.controller;


import com.samsungnomads.wheretogo.domain.test.dto.TestResponseDto;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Test", description = "테스트 API")
public interface TestControllerDocs {

    @Operation(
            summary = "성공 테스트 API",
            description = "데이터 없이 성공 응답만 반환합니다. (data = null)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "요청 성공", useReturnTypeSchema = true)
    })
    public ResponseEntity<SuccessResponse> success();

    @Operation(
            summary = "성공 테스트 API (데이터 포함)",
            description = "임시로 만든 테스트 데이터를 포함하여 성공 응답을 반환합니다. "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", useReturnTypeSchema = true)
    })
    public ResponseEntity<SuccessResponse<TestResponseDto>> successWithData();
}
