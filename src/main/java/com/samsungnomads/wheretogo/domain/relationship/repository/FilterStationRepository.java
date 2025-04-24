package com.samsungnomads.wheretogo.domain.relationship.repository;

import com.samsungnomads.wheretogo.domain.relationship.entity.FilterStation;
import com.samsungnomads.wheretogo.domain.relationship.entity.FilterStationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 필터-역 매핑 리포지토리
 * 💾 필터와 역 간의 관계 데이터에 접근하는 인터페이스
 */
@Repository
public interface FilterStationRepository extends JpaRepository<FilterStation, FilterStationId> {
    
    /**
     * 필터 ID로 필터-역 관계 목록 조회
     * 🔍 특정 필터에 포함된 모든 역 관계 조회
     */
    List<FilterStation> findByFilterId(Long filterId);
    
    /**
     * 역 ID로 필터-역 관계 목록 조회
     * 🔍 특정 역이 포함된 모든 필터 관계 조회
     */
    List<FilterStation> findByStationId(Long stationId);
    
    /**
     * 필터-역 관계 존재 여부 확인
     * ✅ 특정 필터와 역 간의 관계가 존재하는지 확인
     */
    boolean existsByFilterIdAndStationId(Long filterId, Long stationId);
} 