package com.samsungnomads.wheretogo.domain.member.controller;

import com.samsungnomads.wheretogo.domain.member.dto.MemberCreateRequest;
import com.samsungnomads.wheretogo.domain.member.dto.MemberIdResponse;
import com.samsungnomads.wheretogo.domain.member.dto.MemberResponse;
import com.samsungnomads.wheretogo.domain.member.dto.MemberUpdateRequest;
import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.global.error.ErrorResponse;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 회원 컨트롤러 문서화 인터페이스
 * 📚 회원 관련 API 엔드포인트 문서화
 */
@Tag(name = "회원 관리", description = "회원 등록, 조회, 수정, 삭제 API")
public interface MemberControllerDocs {

    /**
     * 현재 로그인한 회원 정보 조회
     * 🔍 현재 인증된 회원의 상세 정보를 조회합니다.
     */
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 회원의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "인증 실패",
                                    value = "{\n" +
                                            "  \"status\": 401,\n" +
                                            "  \"code\": \"A001\",\n" +
                                            "  \"message\": \"인증에 실패했습니다\",\n" +
                                            "  \"errors\": []\n" +
                                            "}"
                            ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SuccessResponse<MemberResponse>> getMyInfo(@AuthenticationPrincipal Member member);


    /**
     * 내 정보 수정
     * 🔄 현재 로그인한 회원 정보를 수정합니다.
     */
    @Operation(summary = "내 정보 수정", description = "현재 로그인한 회원의 정보를 수정합니다.")
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
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "인증 실패",
                                    value = "{\n" +
                                            "  \"status\": 401,\n" +
                                            "  \"code\": \"A001\",\n" +
                                            "  \"message\": \"인증에 실패했습니다\",\n" +
                                            "  \"errors\": []\n" +
                                            "}"
                            ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SuccessResponse> updateMyInfo(
            @AuthenticationPrincipal Member member,
            @Parameter(description = "회원 수정 정보", required = true,
                    schema = @Schema(implementation = MemberUpdateRequest.class))
            @Valid @RequestBody MemberUpdateRequest request);


    /**
     * 내 계정 삭제
     * 🗑️ 현재 로그인한 회원의 계정을 삭제합니다.
     */
    @Operation(summary = "내 계정 삭제", description = "현재 로그인한 회원의 계정을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "삭제 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "인증 실패",
                                    value = "{\n" +
                                            "  \"status\": 401,\n" +
                                            "  \"code\": \"A001\",\n" +
                                            "  \"message\": \"인증에 실패했습니다\",\n" +
                                            "  \"errors\": []\n" +
                                            "}"
                            ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SuccessResponse> deleteMyAccount(@AuthenticationPrincipal Member member);
}
