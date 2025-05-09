package com.samsungnomads.wheretogo.global.security.controller;

import com.samsungnomads.wheretogo.global.security.dto.LoginRequestDto;
import com.samsungnomads.wheretogo.global.security.dto.LogoutRequestDto;
import com.samsungnomads.wheretogo.global.security.dto.SignUpRequestDto;
import com.samsungnomads.wheretogo.global.security.jwt.JwtToken;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "회원가입, 로그인, 로그아웃 API (AccessToken이 필요하지 않습니다)")
public interface AuthControllerDocs {

    @Operation(
            summary = "회원 가입 API",
            description = "loginId, email, password, nickname을 입력해 회원가입을 진행합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "회원가입 성공 응답",
                                    summary = "회원가입 성공 시 응답 예제",
                                    value = """
                                            {
                                                "status": 200,
                                                "message" : "회원가입이 성공적으로 완료되었습니다.",
                                                "data" : null
                                            }
                                            """
                            )
                    ))

    })
    public ResponseEntity<SuccessResponse> signup(@RequestBody SignUpRequestDto signupRequestDto);

    @Operation(
            summary = "로그인 API",
            description = "loginIdOrEmail과 password를 입력하여 로그인합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "로그인 성공 응답",
                                    summary = "로그인 성공 시 응답 예제",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "로그인이 성공적으로 완료되었습니다.",
                                                "data": {
                                                    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.S6BzOV3SruE9_WH8fWlV8zBdG-EpDlxxSZOZpw7UgtE",
                                                    "refreshToken": "s6BzOV3SruE9_WH8fWlV8zBdG-EpDlxxSZOZpw7UgtE"
                                                }
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<JwtToken>> login(@RequestBody LoginRequestDto loginRequestDto);

    @Operation(
            summary = "로그아웃 API",
            description = "accessToken과 refreshToken을 입력하여 로그아웃을 진행합니다. "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "로그아웃 성공 응답",
                                    summary = "로그아웃 성공 시 응답 예제",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "로그아웃이 성공적으로 완료되었습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse> logout(@RequestBody LogoutRequestDto logoutRequestDto);


}
