package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 필터 생성 응답 DTO
 * 📄 생성된 필터 정보 응답
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterSaveResponseDto {
    
    private Long filterId;           // 🔑 필터 ID
    private String name;             // 📝 필터 이름
    private Boolean isShared;        // 🔄 공유 여부
    private List<Long> stationIds;   // 🚉 포함된 역 ID 목록
    private LocalDateTime createdAt; // 📅 생성 시간
    
    /**
     * 빌더 패턴을 이용한 생성자
     * 🏗️ 필터 생성 응답 객체 생성
     */
    @Builder
    public FilterSaveResponseDto(Long filterId, String name, Boolean isShared, List<Long> stationIds, LocalDateTime createdAt) {
        this.filterId = filterId;
        this.name = name;
        this.isShared = isShared;
        this.stationIds = stationIds;
        this.createdAt = createdAt;
    }
    
    /**
     * Entity를 DTO로 변환
     * 🔄 필터 엔티티를 생성 응답 DTO로 변환
     */
    public static FilterSaveResponseDto from(Filter filter) {
        List<Long> stationIds = filter.getStations().stream()
                .map(filterStation -> filterStation.getStation().getId())
                .collect(Collectors.toList());
                
        return FilterSaveResponseDto.builder()
                .filterId(filter.getId())
                .name(filter.getName())
                .isShared(filter.getIsShared())
                .stationIds(stationIds)
                .createdAt(filter.getCreatedAt())
                .build();
    }
} 