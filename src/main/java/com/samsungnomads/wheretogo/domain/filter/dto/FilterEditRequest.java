package com.samsungnomads.wheretogo.domain.filter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 필터 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "필터 수정 요청 DTO")
public class FilterEditRequest {
    @Schema(description = "수정할 필터 ID", example = "1", required = true)
    private Long filterId;      // 수정할 필터 ID
    
    @Schema(description = "필터 이름", example = "수정된 필터 이름", required = false)
    private String name;        // 필터 이름
    
    @Schema(description = "공개 여부", example = "true", required = false)
    private Boolean isShared;   // 공개 여부
    
    @Schema(description = "포함할 역 ID 목록", example = "[1, 2, 3]", required = false)
    private List<Long> stationIds; // 포함할 역 ID 목록
} 