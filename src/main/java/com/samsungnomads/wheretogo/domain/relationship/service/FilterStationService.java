package com.samsungnomads.wheretogo.domain.relationship.service;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.relationship.entity.FilterStation;
import com.samsungnomads.wheretogo.domain.relationship.entity.FilterStationId;
import com.samsungnomads.wheretogo.domain.relationship.repository.FilterStationRepository;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 필터-역 관계 서비스
 * 💼 필터와 역 간의 관계를 처리하는 비즈니스 로직 서비스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FilterStationService {
    
    private final FilterStationRepository filterStationRepository;
    
    /**
     * 필터-역 관계 생성
     * 📝 필터와 역 간의 새로운 관계 생성
     */
    @Transactional
    public FilterStation createFilterStationRelationship(Filter filter, Station station) {
        FilterStation filterStation = FilterStation.builder()
                .filter(filter)
                .station(station)
                .build();
        
        return filterStationRepository.save(filterStation);
    }
    
    /**
     * 필터의 모든 역 관계 조회
     * 🔍 특정 필터에 포함된 모든 역 관계 조회
     */
    public List<FilterStation> findStationsByFilter(Long filterId) {
        return filterStationRepository.findByFilterId(filterId);
    }
    
    /**
     * 역의 모든 필터 관계 조회
     * 🔍 특정 역이 포함된 모든 필터 관계 조회
     */
    public List<FilterStation> findFiltersByStation(Long stationId) {
        return filterStationRepository.findByStationId(stationId);
    }
    
    /**
     * 특정 필터-역 관계 조회
     * 🔍 특정 필터와 역 간의 관계 조회
     */
    public Optional<FilterStation> findFilterStationRelationship(Long filterId, Long stationId) {
        FilterStationId id = new FilterStationId(filterId, stationId);
        return filterStationRepository.findById(id);
    }
    
    /**
     * 필터-역 관계 삭제
     * 🗑️ 필터와 역 간의 관계 제거
     */
    @Transactional
    public void deleteFilterStationRelationship(Long filterId, Long stationId) {
        FilterStationId id = new FilterStationId(filterId, stationId);
        filterStationRepository.deleteById(id);
    }
    
    /**
     * 필터-역 관계 확인
     * ✅ 특정 필터와 역 간의 관계가 존재하는지 확인
     */
    public boolean hasFilterStationRelationship(Long filterId, Long stationId) {
        return filterStationRepository.existsByFilterIdAndStationId(filterId, stationId);
    }
} 