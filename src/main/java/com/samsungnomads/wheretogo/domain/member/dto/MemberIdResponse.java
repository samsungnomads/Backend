package com.samsungnomads.wheretogo.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 ID 응답 DTO
 * 📌 회원 등록 후 생성된 회원 ID 정보를 담는 객체
 * 📢 시스템에서 자동 생성되는 고유 식별자(auto increment)로 로그인 시 사용하는 loginId와는 다릅니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "회원 ID 응답")
public class MemberIdResponse {

    @Schema(
        description = "회원 고유 식별자 (시스템에서 자동 생성되는 Auto Increment ID 값)", 
        example = "1",
        title = "회원 PK"
    )
    private Long memberId;

    /**
     * 생성자
     * 🏗️ 회원 ID로 응답 객체 생성
     */
    public MemberIdResponse(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 정적 팩토리 메서드
     * 🔄 회원 ID로 응답 객체 생성
     */
    public static MemberIdResponse of(Long memberId) {
        return new MemberIdResponse(memberId);
    }
} 