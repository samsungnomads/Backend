package com.samsungnomads.wheretogo.domain.filter.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 필터 다운로드 요청 DTO
 * 📥 공용 저장소에서 내 저장소로 필터 다운로드 요청 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterDownloadRequestDto {
    
    private Long filterId; // 🔍 다운로드할 필터 ID
    
    /**
     * 생성자
     * 📝 필터 다운로드 요청 초기화
     */
    public FilterDownloadRequestDto(Long filterId) {
        this.filterId = filterId;
    }
} 