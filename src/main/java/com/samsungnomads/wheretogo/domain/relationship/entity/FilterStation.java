package com.samsungnomads.wheretogo.domain.relationship.entity;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterStation {

    @Id
    @Column(name = "filter_id", insertable = false, updatable = false)
    private Long filterId; // 🔍 필터 ID 값 (중복 필드)

    @Id
    @Column(name = "station_id", insertable = false, updatable = false)
    private Long stationId; // 🚉 역 ID 값 (중복 필드)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", referencedColumnName = "id")
    private Filter filter; // 🔗 필터 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private Station station; // 🚉 역 참조

    /**
     * 필터-역 관계 생성
     * 📝 필터와 역을 연결
     */
    @Builder
    public FilterStation(Filter filter, Station station) {
        this.filter = filter;
        this.station = station;
        this.filterId = filter.getId();
        this.stationId = station.getId();
    }
} 