package com.samsungnomads.wheretogo.domain.relationship.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * FilterStation 복합키 클래스
 * 🔑 필터-역 매핑 테이블의 복합키를 나타내는 클래스
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FilterStationId implements Serializable {
    
    private Long filterId; // 🔍 필터 ID (FK to filter.id)
    private Long stationId; // 🚉 역 ID (FK to station.id)
} 