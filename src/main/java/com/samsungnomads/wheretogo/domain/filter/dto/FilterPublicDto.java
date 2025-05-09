package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FilterPublicDto {

    private Long id;
    private String name;
    private String creatorNickname;
    private Integer likes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FilterPublicDto from(Filter filter) {
        return new FilterPublicDto(
                filter.getId(),
                filter.getName(),
                //filter.getCreator().getNickname(),
                "Abc",
                filter.getLikes(),
                filter.getCreatedAt(),
                filter.getUpdatedAt()
        );
    }
}
