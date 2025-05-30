package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 필터 저장 응답 DTO
 */
@Getter
@Builder
@Schema(description = "필터 저장 응답 DTO")
public class FilterSaveResponseDto {
    @Schema(description = "필터 ID", example = "1")
    private Long filterId;      // 필터 ID
    
    @Schema(description = "필터 이름", example = "맛집 추천 필터")
    private String name;        // 필터 이름
    
    @Schema(description = "공개 여부", example = "true")
    private Boolean isShared;   // 공개 여부
    
    @Schema(description = "포함된 역 ID 목록", example = "[1, 2, 3]")
    private List<Long> stationIds; // 포함된 역 ID 목록
    
    @Schema(description = "생성 시간", example = "2023-06-30T12:34:56")
    private LocalDateTime createdAt; // 생성 시간

    /**
     * 필터 엔티티로부터 응답 DTO 생성
     */
    public static FilterSaveResponseDto from(Filter filter) {
        // 필터에 포함된 역 ID 목록 추출
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