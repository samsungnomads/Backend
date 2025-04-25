package com.samsungnomads.wheretogo.domain.member.controller;

import com.samsungnomads.wheretogo.domain.member.dto.MemberCreateRequest;
import com.samsungnomads.wheretogo.domain.member.dto.MemberIdResponse;
import com.samsungnomads.wheretogo.domain.member.dto.MemberResponse;
import com.samsungnomads.wheretogo.domain.member.dto.MemberUpdateRequest;
import com.samsungnomads.wheretogo.global.error.ErrorResponse;
import com.samsungnomads.wheretogo.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 회원 컨트롤러 문서화 인터페이스
 * 📚 회원 관련 API 엔드포인트 문서화
 */
@Tag(name = "회원 관리", description = "회원 등록, 조회, 수정, 삭제 API")
public interface MemberControllerDocs {

    /**
     * 회원 목록 조회
     * 🔍 전체 회원 목록을 조회합니다.
     */
    @Operation(summary = "회원 목록 조회", description = "등록된 모든 회원 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", 
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            name = "서버 오류",
                            value = "{\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"code\": \"C004\",\n" +
                                    "  \"message\": \"서버 내부 오류가 발생했습니다\",\n" +
                                    "  \"errors\": []\n" +
                                    "}"
                    )))
    })
    ResponseEntity<SuccessResponse<List<MemberResponse>>> getAllMembers();

    /**
     * 회원 상세 조회
     * 🔍 특정 회원의 상세 정보를 조회합니다.
     */
    @Operation(summary = "회원 상세 조회", description = "회원 ID로 특정 회원의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공", 
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 없음", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            name = "회원 없음",
                            value = "{\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"code\": \"M002\",\n" +
                                    "  \"message\": \"회원을 찾을 수 없습니다. ID: 123\",\n" +
                                    "  \"errors\": []\n" +
                                    "}"
                    ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SuccessResponse<MemberResponse>> getMember(
            @Parameter(description = "회원 고유 식별자", required = true) @PathVariable Long id);

    /**
     * 회원 등록
     * 📝 새로운 회원을 등록합니다.
     */
    @Operation(
        summary = "회원 등록", 
        description = "새로운 회원 정보를 등록합니다. 응답으로 시스템에서 자동 생성된 회원 ID(PK)를 반환합니다. " +
                     "이 ID는 Auto Increment 형식으로 생성되며, 회원의 로그인 ID(loginId)와는 다릅니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201", 
                description = "등록 성공 - 생성된 회원의 시스템 ID(PK)가 반환됨", 
                content = @Content(schema = @Schema(implementation = SuccessResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            name = "유효성 검증 실패",
                            value = "{\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"code\": \"C001\",\n" +
                                    "  \"message\": \"잘못된 입력값입니다\",\n" +
                                    "  \"errors\": [\n" +
                                    "    {\n" +
                                    "      \"field\": \"email\",\n" +
                                    "      \"value\": \"invalid-email\",\n" +
                                    "      \"reason\": \"유효한 이메일 형식이 아닙니다\"\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                    ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복된 회원", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "이메일 중복",
                                    value = "{\n" +
                                            "  \"status\": 409,\n" +
                                            "  \"code\": \"M001\",\n" +
                                            "  \"message\": \"이미 사용 중인 이메일입니다: user@example.com\",\n" +
                                            "  \"errors\": []\n" +
                                            "}"
                            ),
                            @ExampleObject(
                                    name = "아이디 중복",
                                    value = "{\n" +
                                            "  \"status\": 409,\n" +
                                            "  \"code\": \"M004\",\n" +
                                            "  \"message\": \"이미 사용 중인 아이디입니다: hong123\",\n" +
                                            "  \"errors\": []\n" +
                                            "}"
                            )
                    })),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SuccessResponse<MemberIdResponse>> createMember(
            @Parameter(description = "회원 등록 정보", required = true, 
                    schema = @Schema(implementation = MemberCreateRequest.class)) 
            @Valid @RequestBody MemberCreateRequest request);

    /**
     * 회원 정보 수정
     * 🔄 기존 회원 정보를 수정합니다.
     */
    @Operation(summary = "회원 정보 수정", description = "회원 ID로 기존 회원 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공", 
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            name = "유효성 검증 실패",
                            value = "{\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"code\": \"C001\",\n" +
                                    "  \"message\": \"잘못된 입력값입니다\",\n" +
                                    "  \"errors\": [\n" +
                                    "    {\n" +
                                    "      \"field\": \"password\",\n" +
                                    "      \"value\": \"weak\",\n" +
                                    "      \"reason\": \"비밀번호는 8~20자 사이어야 합니다\"\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                    ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 없음", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            name = "회원 없음",
                            value = "{\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"code\": \"M002\",\n" +
                                    "  \"message\": \"회원을 찾을 수 없습니다. ID: 123\",\n" +
                                    "  \"errors\": []\n" +
                                    "}"
                    ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SuccessResponse> updateMember(
            @Parameter(description = "회원 고유 식별자", required = true) @PathVariable Long id,
            @Parameter(description = "회원 수정 정보", required = true, 
                    schema = @Schema(implementation = MemberUpdateRequest.class)) 
            @Valid @RequestBody MemberUpdateRequest request);

    /**
     * 회원 삭제
     * 🗑️ 기존 회원을 삭제합니다.
     */
    @Operation(summary = "회원 삭제", description = "회원 ID로 기존 회원을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "삭제 성공", 
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원 없음", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            name = "회원 없음",
                            value = "{\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"code\": \"M002\",\n" +
                                    "  \"message\": \"회원을 찾을 수 없습니다. ID: 123\",\n" +
                                    "  \"errors\": []\n" +
                                    "}"
                    ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류", 
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SuccessResponse> deleteMember(
            @Parameter(description = "회원 고유 식별자", required = true) @PathVariable Long id);
}
