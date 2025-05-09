package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 필터 업로드 응답 DTO
 * 📩 필터 업로드 결과 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterUploadResponseDto {
    
    private Long filterId;        // 🔑 업로드된 필터 ID
    private String filterName;    // 📝 필터 이름
    private Boolean isShared;     // 🔄 공유 상태
    
    /**
     * 빌더 패턴을 이용한 생성자
     * 🏗️ 필터 업로드 응답 객체 생성
     */
    @Builder
    public FilterUploadResponseDto(Long filterId, String filterName, Boolean isShared) {
        this.filterId = filterId;
        this.filterName = filterName;
        this.isShared = isShared;
    }
    
    /**
     * Entity를 DTO로 변환
     * 🔄 필터 엔티티를 업로드 응답 DTO로 변환
     */
    public static FilterUploadResponseDto from(Filter filter) {
        return FilterUploadResponseDto.builder()
                .filterId(filter.getId())
                .filterName(filter.getName())
                .isShared(filter.getIsShared())
                .build();
    }
} 