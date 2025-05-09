package com.samsungnomads.wheretogo.domain.filter.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 필터 생성 요청 DTO
 * 📝 새 필터 생성 요청 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterSaveRequestDto {
    
    private String name;            // 📝 필터 이름
    private Boolean isShared;       // 🔄 공유 여부
    private List<Long> stationIds;  // 🚉 포함할 역 ID 목록
    
    /**
     * 생성자
     * 🏗️ 필터 생성 요청 초기화
     */
    public FilterSaveRequestDto(String name, Boolean isShared, List<Long> stationIds) {
        this.name = name;
        this.isShared = isShared;
        this.stationIds = stationIds;
    }
} 