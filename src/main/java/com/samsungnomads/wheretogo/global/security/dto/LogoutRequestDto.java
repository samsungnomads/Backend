package com.samsungnomads.wheretogo.global.security.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그아웃 요청 DTO
 * 🔒 로그아웃 시 필요한 토큰 정보를 담는 객체
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutRequestDto {
    
    private String accessToken;
    private String refreshToken; 
    
  
    public LogoutRequestDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
} 