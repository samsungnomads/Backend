package com.samsungnomads.wheretogo.domain.relationship.entity;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 필터-역 매핑 엔티티
 * 🔄 필터와 역 간의 다대다 관계를 표현하는 연결 테이블 엔티티
 */
@Entity
@Table(name = "filter_station")
@IdClass(FilterStationId.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterStation {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;
} 