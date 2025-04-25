package com.samsungnomads.wheretogo.domain.relationship.service;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilter;
import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilterId;
import com.samsungnomads.wheretogo.domain.relationship.repository.MemberFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 회원-필터 관계 서비스
 * 💼 회원과 필터 간의 관계를 처리하는 비즈니스 로직 서비스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberFilterService {
    
    private final MemberFilterRepository memberFilterRepository;
    
    /**
     * 회원-필터 관계 생성
     * 📝 회원과 필터 간의 새로운 관계 생성
     */
    @Transactional
    public MemberFilter createMemberFilterRelationship(Member member, Filter filter) {
        MemberFilter memberFilter = MemberFilter.builder()
                .member(member)
                .filter(filter)
                .build();
        
        return memberFilterRepository.save(memberFilter);
    }
    
    /**
     * 회원의 모든 필터 관계 조회
     * 🔍 특정 회원이 접근 가능한 모든 필터 관계 조회
     */
    public List<MemberFilter> findFiltersByMember(Long uid) {
        return memberFilterRepository.findByMemberUid(uid);
    }
    
    /**
     * 필터의 모든 회원 관계 조회
     * 🔍 특정 필터에 접근 가능한 모든 회원 관계 조회
     */
    public List<MemberFilter> findMembersByFilter(Long filterId) {
        return memberFilterRepository.findByFilterId(filterId);
    }
    
    /**
     * 특정 회원-필터 관계 조회
     * 🔍 특정 회원과 필터 간의 관계 조회
     */
    public Optional<MemberFilter> findMemberFilterRelationship(Long uid, Long filterId) {
        MemberFilterId id = new MemberFilterId(uid, filterId);
        return memberFilterRepository.findById(id);
    }
    
    /**
     * 회원-필터 관계 삭제
     * 🗑️ 회원과 필터 간의 관계 제거
     */
    @Transactional
    public void deleteMemberFilterRelationship(Long uid, Long filterId) {
        MemberFilterId id = new MemberFilterId(uid, filterId);
        memberFilterRepository.deleteById(id);
    }
    
    /**
     * 회원-필터 관계 확인
     * ✅ 특정 회원이 특정 필터에 접근 가능한지 확인
     */
    public boolean hasMemberFilterRelationship(Long uid, Long filterId) {
        return memberFilterRepository.existsByMemberUidAndFilterId(uid, filterId);
    }
} 