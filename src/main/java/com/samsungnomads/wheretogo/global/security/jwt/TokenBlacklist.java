package com.samsungnomads.wheretogo.global.security.jwt;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 토큰 블랙리스트
 * 🔒 로그아웃된 토큰을 관리하는 컴포넌트
 */
@Component
public class TokenBlacklist {

    // 💾 블랙리스트에 등록된 액세스 토큰
    private final Set<String> blacklistedAccessTokens = ConcurrentHashMap.newKeySet();
    
    // 💾 블랙리스트에 등록된 리프레시 토큰
    private final Set<String> blacklistedRefreshTokens = ConcurrentHashMap.newKeySet();

    /**
     * 액세스 토큰 블랙리스트에 추가
     * 🚫 로그아웃된 액세스 토큰을 블랙리스트에 추가
     */
    public void addToBlacklist(String accessToken) {
        blacklistedAccessTokens.add(accessToken);
    }

    /**
     * 리프레시 토큰 블랙리스트에 추가
     * 🚫 로그아웃된 리프레시 토큰을 블랙리스트에 추가
     */
    public void addRefreshTokenToBlacklist(String refreshToken) {
        blacklistedRefreshTokens.add(refreshToken);
    }

    /**
     * 토큰이 블랙리스트에 있는지 확인
     * ✅ 액세스 토큰이 블랙리스트에 등록되었는지 확인
     */
    public boolean isBlacklisted(String accessToken) {
        return blacklistedAccessTokens.contains(accessToken);
    }

    /**
     * 리프레시 토큰이 블랙리스트에 있는지 확인
     * ✅ 리프레시 토큰이 블랙리스트에 등록되었는지 확인
     */
    public boolean isRefreshTokenBlacklisted(String refreshToken) {
        return blacklistedRefreshTokens.contains(refreshToken);
    }
} 