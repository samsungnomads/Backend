package com.samsungnomads.wheretogo.domain.relationship.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 필터-역 매핑 복합 키 클래스
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FilterStationId implements Serializable {
    private Long filter;   // Filter 엔티티의 ID
    private Long station;  // Station 엔티티의 ID
} 