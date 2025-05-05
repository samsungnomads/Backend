package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 필터 조회 응답 DTO
 */
@Getter
@AllArgsConstructor
public class FilterPrivateOwnDto {

    private Long id;
    private String name;
    private String creatorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FilterPrivateOwnDto from(Filter filter) {
        return new FilterPrivateOwnDto(
                filter.getId(),
                filter.getName(),
                filter.getCreator().getNickname(),
                filter.getCreatedAt(),
                filter.getUpdatedAt()
        );
    }
}
