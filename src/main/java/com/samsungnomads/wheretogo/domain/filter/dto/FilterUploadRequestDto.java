package com.samsungnomads.wheretogo.domain.filter.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 필터 업로드 요청 DTO
 * 📤 내 저장소에서 공용 저장소로 필터 업로드 요청 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterUploadRequestDto {
    
    private Long filterId;      // 🔍 업로드할 필터 ID
    private Boolean isShared;   // 🔄 공유 여부
    
    /**
     * 생성자
     * 📝 필터 업로드 요청 초기화
     */
    public FilterUploadRequestDto(Long filterId, Boolean isShared) {
        this.filterId = filterId;
        this.isShared = isShared;
    }
} 