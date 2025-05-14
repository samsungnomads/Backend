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
     * 공용 저장소에서 내 저장소로 필터 다운로드
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
     * 내 저장소의 필터를 공용 저장소로 업로드(공유)
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
     * 새로운 필터를 생성하고 내 저장소에 저장
     */
    @Transactional
    public FilterSaveResponseDto createFilter(String loginId, String name, Boolean isShared, List<Long> stationIds) {
        // 입력값 검증
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "필터 이름은 필수입니다.");
        }
        
        if (isShared == null) {
            isShared = false; // 기본값 설정
        }
        
        // 역 목록 검증
        if (stationIds == null || stationIds.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "최소 하나 이상의 역을 선택해야 합니다.");
        }
        
        // 중복된 역 ID 제거
        List<Long> uniqueStationIds = stationIds.stream().distinct().collect(Collectors.toList());
        if (uniqueStationIds.size() != stationIds.size()) {
            log.warn("중복된 역 ID가 제거되었습니다. 원본: {}, 중복 제거: {}", stationIds.size(), uniqueStationIds.size());
        }
        
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
        
        try {
            // 필터에 역 추가
            List<Station> stations = stationRepository.findAllById(uniqueStationIds);
            
            // 요청한 모든 역이 존재하는지 확인
            if (stations.size() != uniqueStationIds.size()) {
                // 존재하지 않는 역 ID 찾기
                List<Long> foundStationIds = stations.stream()
                        .map(Station::getId)
                        .collect(Collectors.toList());
                List<Long> notFoundStationIds = uniqueStationIds.stream()
                        .filter(id -> !foundStationIds.contains(id))
                        .collect(Collectors.toList());
                
                throw new BusinessException(ErrorCode.STATION_NOT_FOUND, "존재하지 않는 역 ID: " + notFoundStationIds);
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
            
            // 응답 생성 (요청받은 stationIds를 직접 사용)
            return FilterSaveResponseDto.builder()
                    .filterId(savedFilter.getId())
                    .name(savedFilter.getName())
                    .isShared(savedFilter.getIsShared())
                    .stationIds(uniqueStationIds)  // 중복 제거된 역 ID 목록 사용
                    .createdAt(savedFilter.getCreatedAt())
                    .build();
        } catch (Exception e) {
            // 필터 생성 중 오류 발생 시 롤백을 위해 예외 다시 던지기
            if (!(e instanceof BusinessException)) {
                log.error("필터 생성 중 오류 발생: {}", e.getMessage(), e);
                throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "필터 생성 중 오류가 발생했습니다.");
            }
            throw e;
        }
    }

    /**
     * 필터 좋아요 토글
     * 필터의 좋아요 수를 증가시킵니다.
     * 
     * @param loginId 사용자 로그인 ID
     * @param request 필터 좋아요 요청 정보
     * @return 좋아요 응답 정보
     */
    @Transactional
    public FilterLikeResponseDto toggleFilterLike(String loginId, FilterLikeRequest request) {
        // 입력값 검증
        if (request.getFilterId() == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "필터 ID는 필수입니다.");
        }
        
        // 사용자 확인
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND, loginId));
        
        // 필터 확인
        Filter filter = filterRepository.findById(request.getFilterId())
                .orElseThrow(() -> new BusinessException(ErrorCode.FILTER_NOT_FOUND, request.getFilterId().toString()));

        try {
            // 좋아요 토글 (현재는 단순히 좋아요 수만 증가)
            Integer currentLikes = filter.getLikes();
            if (currentLikes == null) {
                currentLikes = 0;
            }
            
            // 좋아요 수 증가
            Integer newLikes = currentLikes + 1;
            filter.updateLikes(newLikes);
            filterRepository.save(filter);
            
            return FilterLikeResponseDto.of(filter.getId(), newLikes, true);
        } catch (Exception e) {
            // 좋아요 처리 중 오류 발생 시 롤백을 위해 예외 다시 던지기
            if (!(e instanceof BusinessException)) {
                log.error("좋아요 처리 중 오류 발생: {}", e.getMessage(), e);
                throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "좋아요 처리 중 오류가 발생했습니다.");
            }
            throw e;
        }
    }

    /**
     * 필터 수정
     * 기존 필터의 이름, 공개 여부, 포함된 역을 수정합니다.
     * 
     * @param loginId 사용자 로그인 ID
     * @param request 필터 수정 요청 정보
     * @return 수정된 필터 정보
     */
    @Transactional
    public FilterEditResponseDto editFilter(String loginId, FilterEditRequest request) {
        // 입력값 검증
        if (request.getFilterId() == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "필터 ID는 필수입니다.");
        }
        
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "필터 이름은 비어있을 수 없습니다.");
        }
        
        // 사용자 확인
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND, loginId));
        
        // 필터 확인
        Filter filter = filterRepository.findById(request.getFilterId())
                .orElseThrow(() -> new BusinessException(ErrorCode.FILTER_NOT_FOUND, request.getFilterId().toString()));
        
        // 필터의 생성자 또는 공유받은 회원인지 확인
        boolean isCreator = filter.getCreator().getLoginId().equals(loginId);
        boolean isSharedMember = memberFilterRepository.existsByMemberIdAndFilterId(member.getId(), filter.getId());
        
        if (!isCreator && !isSharedMember) {
            throw new BusinessException(ErrorCode.FILTER_NOT_OWNER);
        }
        
        try {
            // 필터 기본 정보 수정 (생성자만 가능)
            if (isCreator) {
                filter.update(request.getName(), request.getIsShared());
            } else {
                // 공유받은 회원은 이름만 수정 가능
                filter.update(request.getName(), null);
            }
            
            // 역 목록 업데이트 (기존 역 삭제 후 새 역 추가)
            if (request.getStationIds() != null) {
                if (request.getStationIds().isEmpty()) {
                    throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "최소 하나 이상의 역을 선택해야 합니다.");
                }
                
                // 중복된 역 ID 제거
                List<Long> uniqueStationIds = request.getStationIds().stream().distinct().collect(Collectors.toList());
                if (uniqueStationIds.size() != request.getStationIds().size()) {
                    log.warn("중복된 역 ID가 제거되었습니다. 원본: {}, 중복 제거: {}", request.getStationIds().size(), uniqueStationIds.size());
                }
                
                // 기존 필터-역 관계 삭제
                filterStationRepository.deleteAllByFilterId(filter.getId());
                
                // 요청한 역 목록 조회
                List<Station> stations = stationRepository.findAllById(uniqueStationIds);
                
                // 요청한 모든 역이 존재하는지 확인
                if (stations.size() != uniqueStationIds.size()) {
                    // 존재하지 않는 역 ID 찾기
                    List<Long> foundStationIds = stations.stream()
                            .map(Station::getId)
                            .collect(Collectors.toList());
                    List<Long> notFoundStationIds = uniqueStationIds.stream()
                            .filter(id -> !foundStationIds.contains(id))
                            .collect(Collectors.toList());
                    
                    throw new BusinessException(ErrorCode.STATION_NOT_FOUND, "존재하지 않는 역 ID: " + notFoundStationIds);
                }
                
                // 새 필터-역 관계 생성
                List<FilterStation> filterStations = stations.stream()
                        .map(station -> FilterStation.builder()
                                .filter(filter)
                                .station(station)
                                .build())
                        .collect(Collectors.toList());
                
                filterStationRepository.saveAll(filterStations);
            }
            
            // 변경사항 저장
            Filter updatedFilter = filterRepository.save(filter);
            
            // 응답 생성
            return FilterEditResponseDto.from(updatedFilter);
        } catch (Exception e) {
            // 필터 수정 중 오류 발생 시 롤백을 위해 예외 다시 던지기
            if (!(e instanceof BusinessException)) {
                log.error("필터 수정 중 오류 발생: {}", e.getMessage(), e);
                throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "필터 수정 중 오류가 발생했습니다.");
            }
            throw e;
        }
    }
}