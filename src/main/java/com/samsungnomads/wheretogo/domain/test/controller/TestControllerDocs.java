package com.samsungnomads.wheretogo.domain.test.controller;


import com.samsungnomads.wheretogo.domain.test.dto.AuthTestResponseDto;
import com.samsungnomads.wheretogo.domain.test.dto.TestResponseDto;
import com.samsungnomads.wheretogo.global.security.dto.UserDetailsImpl;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Test", description = "테스트 API (AccessToken이 필요하지 않습니다)")
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

    @Operation(
            summary = "인증 정보 조회 테스트 API",
            description = "인증된 사용자의 정보를 조회합니다. 인증된 사용자만 접근할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "인증된 사용자 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "인증 정보 조회 성공 응답",
                                    summary = "인증된 사용자의 사용자명과 권한 목록",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "인증된 사용자 정보 조회 성공",
                                                "data": {
                                                    "username": "user1",
                                                    "authorities": ["ROLE_USER"]
                                                }
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<AuthTestResponseDto>> auth(@AuthenticationPrincipal UserDetailsImpl userDetails);
}
