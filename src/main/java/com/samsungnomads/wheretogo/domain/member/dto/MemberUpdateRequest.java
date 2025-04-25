package com.samsungnomads.wheretogo.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 정보 수정 요청 DTO
 * 🔄 회원 정보 수정 시 필요한, 검증된 정보를 담고 있는 객체
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "회원 정보 수정 요청")
public class MemberUpdateRequest {

    @Schema(description = "회원 비밀번호", example = "NewPassword123!")
    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이어야 합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", 
            message = "비밀번호는 최소 8자 이상이며, 하나 이상의 문자, 숫자, 특수 문자를 포함해야 합니다")
    private String password;

    @Schema(description = "회원 닉네임", example = "새로운닉네임")
    @NotBlank(message = "닉네임은 필수 입력값입니다")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자 사이어야 합니다")
    private String nickname;

    /**
     * 빌더 패턴을 이용한 생성자
     * 🏗️ 회원 정보 수정 요청 객체 생성
     */
    @Builder
    public MemberUpdateRequest(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
} 