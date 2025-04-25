package com.samsungnomads.wheretogo.domain.member.dto;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 생성 요청 DTO
 * 📝 회원 가입 시 필요한, 검증된 정보를 담고 있는 객체
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "회원 생성 요청")
public class MemberCreateRequest {

    @Schema(description = "회원 로그인 아이디", example = "hong123")
    @NotBlank(message = "로그인 아이디는 필수 입력값입니다")
    @Size(min = 4, max = 20, message = "로그인 아이디는 4~20자 사이어야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "로그인 아이디는 영문자, 숫자, 언더스코어만 허용됩니다")
    private String loginId;

    @Schema(description = "회원 이메일", example = "user@example.com")
    @NotBlank(message = "이메일은 필수 입력값입니다")
    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

    @Schema(description = "회원 비밀번호", example = "Password123!")
    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이어야 합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", 
            message = "비밀번호는 최소 8자 이상이며, 하나 이상의 문자, 숫자, 특수 문자를 포함해야 합니다")
    private String password;

    @Schema(description = "회원 닉네임", example = "홍길동")
    @NotBlank(message = "닉네임은 필수 입력값입니다")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자 사이어야 합니다")
    private String nickname;

    /**
     * 빌더 패턴을 이용한 생성자
     * 🏗️ 회원 생성 요청 객체 생성
     */
    @Builder
    public MemberCreateRequest(String loginId, String email, String password, String nickname) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * DTO를 Entity로 변환
     * 🔄 회원 생성 요청 DTO를 회원 엔티티로 변환
     */
    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
} 