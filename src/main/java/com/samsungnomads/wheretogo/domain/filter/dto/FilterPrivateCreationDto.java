package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FilterPrivateCreationDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FilterPrivateCreationDto from(Filter filter) {
        return new FilterPrivateCreationDto(
                filter.getId(),
                filter.getName(),
                filter.getCreatedAt(),
                filter.getUpdatedAt()
        );
    }
}
