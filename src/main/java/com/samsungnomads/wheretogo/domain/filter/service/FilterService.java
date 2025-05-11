package com.samsungnomads.wheretogo.domain.filter.service;

import com.samsungnomads.wheretogo.domain.filter.dto.*;
import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.filter.repository.FilterRepository;
import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import com.samsungnomads.wheretogo.domain.relationship.entity.FilterStation;
import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilter;
import com.samsungnomads.wheretogo.domain.relationship.repository.FilterStationRepository;
import com.samsungnomads.wheretogo.domain.relationship.repository.MemberFilterRepository;
import com.samsungnomads.wheretogo.domain.relationship.service.MemberFilterService;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import com.samsungnomads.wheretogo.domain.station.repository.StationRepository;
import com.samsungnomads.wheretogo.global.error.ErrorCode;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;
    private final MemberFilterRepository memberFilterRepository;
    private final MemberRepository memberRepository;
    private final MemberFilterService memberFilterService;
    private final StationRepository stationRepository;
    private final FilterStationRepository filterStationRepository;

    @Transactional
    public List<FilterPrivateOwnDto> getPrivateOwnFiltersByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND, loginId));

        List<MemberFilter> memberFilters = memberFilterRepository.findByMemberId(member.getId());

        return memberFilters.stream()
                .map(memberFilter -> FilterPrivateOwnDto.from(memberFilter.getFilter()))
                .toList();
    }

    @Transactional
    public List<FilterPrivateCreationDto> getPrivateCreationFiltersByLoginId(String loginId) {
        List<Filter> filters = filterRepository.findByCreator_LoginId(loginId);

        return filters.stream()
                .map(FilterPrivateCreationDto::from)
                .toList();
    }

    @Transactional
    public FilterDetailDto getFilterDetail(Long filterId) {
        Filter filter = filterRepository.findById(filterId).orElseThrow(() -> new BusinessException(ErrorCode.FILTER_NOT_FOUND));

        return FilterDetailDto.from(filter);
    }

    @Transactional
    public Slice<FilterPublicDto> getPublicFilters(Long cursorId, FilterConditionDto condition, Pageable pageable) {

        return filterRepository.findByConditionWithPageable(cursorId, condition, pageable);
    }

    @Transactional
    public void deleteFilter(String username, Long filterId) {
        Filter filter = filterRepository.findById(filterId).orElseThrow(() -> new BusinessException(ErrorCode.FILTER_NOT_FOUND));
        Member member = memberRepository.findByLoginId(username).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        MemberFilter memberFilter = memberFilterRepository.findByMemberIdAndFilterId(member.getId(), filter.getId()).orElseThrow(() -> new BusinessException(ErrorCode.FILTER_NOT_OWNER));

        memberFilterRepository.delete(memberFilter);
    }
    
    /**
     * 필터 다운로드
     * 📥 공용 저장소에서 내 저장소로 필터 다운로드
     */
    @Transactional
    public FilterDownloadResponseDto downloadFilter(String loginId, Long filterId) {
        // 사용자 확인
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND, loginId));
        
        // 필터 확인
        Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILTER_NOT_FOUND));
                
        // 필터가 공유되어 있는지 확인
        if (!filter.getIsShared()) {
            throw new BusinessException(ErrorCode.FILTER_NOT_SHARED);
        }
        
        // 이미 다운로드한 필터인지 확인
        if (memberFilterService.hasMemberFilterRelationship(member.getId(), filter.getId())) {
            throw new BusinessException(ErrorCode.FILTER_ALREADY_DOWNLOADED);
        }
        
        // 회원-필터 관계 생성
        memberFilterService.createMemberFilterRelationship(member, filter);
        
        // 응답 생성
        return FilterDownloadResponseDto.from(filter);
    }
    
    /**
     * 필터 업로드(공유)
     * 📤 내 저장소의 필터를 공용 저장소로 업로드(공유)
     */
    @Transactional
    public FilterUploadResponseDto uploadFilter(String loginId, Long filterId, Boolean isShared) {
        // 사용자 확인
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND, loginId));
        
        // 필터 확인
        Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILTER_NOT_FOUND));
        
        // 필터 제작자인지 확인
        if (!filter.getCreator().getLoginId().equals(loginId)) {
            throw new BusinessException(ErrorCode.FILTER_NOT_CREATOR);
        }
        
        // 이미 공유된 상태이고 공유 요청인 경우
        if (filter.getIsShared() && isShared) {
            throw new BusinessException(ErrorCode.FILTER_ALREADY_SHARED);
        }
        
        // 필터 공유 상태 업데이트
        filter.update(filter.getName(), isShared);
        
        // 변경 사항 저장
        Filter updatedFilter = filterRepository.save(filter);
        
        // 응답 생성
        return FilterUploadResponseDto.from(updatedFilter);
    }
    
    /**
     * 필터 생성
     * 🆕 새로운 필터를 생성하고 내 저장소에 저장
     */
    @Transactional
    public FilterSaveResponseDto createFilter(String loginId, String name, Boolean isShared, List<Long> stationIds) {
        // 사용자 확인
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND, loginId));
        
        // 필터 엔티티 생성 및 저장
        Filter filter = Filter.builder()
                .name(name)
                .isShared(isShared)
                .creator(member)
                .build();
        
        Filter savedFilter = filterRepository.save(filter);
        
        // 필터에 역 추가
        List<Station> stations = stationRepository.findAllById(stationIds);
        
        // 요청한 모든 역이 존재하는지 확인
        if (stations.size() != stationIds.size()) {
            throw new BusinessException(ErrorCode.STATION_NOT_FOUND);
        }
        
        // 필터-역 관계 생성
        List<FilterStation> filterStations = stations.stream()
                .map(station -> FilterStation.builder()
                        .filter(savedFilter)
                        .station(station)
                        .build())
                .collect(Collectors.toList());
        
        filterStationRepository.saveAll(filterStations);
        
        // 회원-필터 관계 생성 (생성자가 소유)
        memberFilterService.createMemberFilterRelationship(member, savedFilter);
        
        // 응답 생성
        return FilterSaveResponseDto.from(savedFilter);
    }
}