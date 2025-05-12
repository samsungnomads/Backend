package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 필터 수정 응답 DTO
 */
@Getter
@Builder
@Schema(description = "필터 수정 응답 DTO")
public class FilterEditResponseDto {
    @Schema(description = "필터 ID", example = "1")
    private Long id;             // 필터 ID
    
    @Schema(description = "필터 이름", example = "수정된 필터 이름")
    private String name;         // 필터 이름
    
    @Schema(description = "공개 여부", example = "true")
    private Boolean isShared;    // 공개 여부
    
    @Schema(description = "수정 시간", example = "2023-06-30T12:34:56")
    private LocalDateTime updatedAt; // 수정 시간

    /**
     * 필터 엔티티로부터 응답 DTO 생성
     */
    public static FilterEditResponseDto from(Filter filter) {
        // 필터에 포함된 역 ID 목록 추출
        List<Long> stationIds = filter.getStations().stream()
                .map(filterStation -> filterStation.getStation().getId())
                .collect(Collectors.toList());
                
        return FilterEditResponseDto.builder()
                .id(filter.getId())
                .name(filter.getName())
                .isShared(filter.getIsShared())
                .updatedAt(filter.getUpdatedAt())
                .build();
    }
} 