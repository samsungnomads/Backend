package com.samsungnomads.wheretogo.domain.filter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 필터 좋아요 요청 DTO
 */
@Getter
@Setter
@Schema(description = "필터 좋아요 요청 DTO")
public class FilterLikeRequest {
    @Schema(description = "좋아요할 필터 ID", example = "1", required = true)
    private Long filterId;  // 좋아요할 필터 ID
} 