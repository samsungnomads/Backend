package com.samsungnomads.wheretogo.global.security.dto;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class SignUpRequestDto {
    private String loginId;
    private String email;
    private String password;
    private String nickname;

    public Member toEntity(String encodedPassword, List<String> roles) {

        return Member.builder()
                .loginId(loginId)
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .roles(roles)
                .build();
    }
}
