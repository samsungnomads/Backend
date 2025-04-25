package com.samsungnomads.wheretogo.domain.member.dto;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 회원 응답 DTO
 * 📄 회원 정보 조회 결과를 담고 있는 객체
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "회원 정보 응답")
public class MemberResponse {

    @Schema(description = "회원 고유 식별자", example = "1")
    private Long uid;

    @Schema(description = "회원 아이디", example = "hong123")
    private String id;

    @Schema(description = "회원 이메일", example = "user@example.com")
    private String email;

    @Schema(description = "회원 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "생성 일시", example = "2023-08-01T12:34:56")
    private LocalDateTime createdAt;

    @Schema(description = "수정 일시", example = "2023-08-02T12:34:56")
    private LocalDateTime updatedAt;

    /**
     * 빌더 패턴을 이용한 생성자
     * 🏗️ 회원 응답 객체 생성
     */
    @Builder
    public MemberResponse(Long uid, String id, String email, String nickname, 
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.uid = uid;
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Entity를 DTO로 변환
     * 🔄 회원 엔티티를 회원 응답 DTO로 변환
     */
    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .uid(member.getUid())
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
} 