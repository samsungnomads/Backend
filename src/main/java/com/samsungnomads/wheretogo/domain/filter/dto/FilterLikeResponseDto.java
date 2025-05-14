package com.samsungnomads.wheretogo.domain.filter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 필터 좋아요 응답 DTO
 */
@Getter
@Builder
@Schema(description = "필터 좋아요 응답 DTO")
public class FilterLikeResponseDto {
    @Schema(description = "필터 ID", example = "1")
    private Long filterId;    // 필터 ID
    
    @Schema(description = "변경된 좋아요 수", example = "5")
    private Integer likes;    // 변경된 좋아요 수
    
    @Schema(description = "좋아요 상태", example = "true")
    private Boolean isLiked;  // 좋아요 상태
    
    public static FilterLikeResponseDto of(Long filterId, Integer likes, Boolean isLiked) {
        return FilterLikeResponseDto.builder()
                .filterId(filterId)
                .likes(likes)
                .isLiked(isLiked)
                .build();
    }
} 