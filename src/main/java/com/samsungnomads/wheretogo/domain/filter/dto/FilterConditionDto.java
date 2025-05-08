package com.samsungnomads.wheretogo.domain.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FilterConditionDto {
    private Integer lastLikes;
    private LocalDateTime lastUpdatedAt;
}
