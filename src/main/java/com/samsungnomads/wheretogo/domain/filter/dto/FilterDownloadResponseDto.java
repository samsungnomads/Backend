package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 필터 다운로드 응답 DTO
 * 📤 필터 다운로드 결과 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterDownloadResponseDto {
    
    private Long filterId;        // 🔑 다운로드된 필터 ID
    private String filterName;    // 📝 필터 이름
    private String creatorId;     // 👤 원 제작자 ID
    
    /**
     * 빌더 패턴을 이용한 생성자
     * 🏗️ 필터 다운로드 응답 객체 생성
     */
    @Builder
    public FilterDownloadResponseDto(Long filterId, String filterName, String creatorId) {
        this.filterId = filterId;
        this.filterName = filterName;
        this.creatorId = creatorId;
    }
    
    /**
     * Entity를 DTO로 변환
     * 🔄 필터 엔티티를 다운로드 응답 DTO로 변환
     */
    public static FilterDownloadResponseDto from(Filter filter) {
        return FilterDownloadResponseDto.builder()
                .filterId(filter.getId())
                .filterName(filter.getName())
                .creatorId(filter.getCreator().getLoginId())
                .build();
    }
} 