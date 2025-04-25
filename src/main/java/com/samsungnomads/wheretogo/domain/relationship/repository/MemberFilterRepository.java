package com.samsungnomads.wheretogo.domain.relationship.repository;

import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilter;
import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 회원-필터 매핑 리포지토리
 * 💾 회원과 필터 간의 관계 데이터에 접근하는 인터페이스
 */
@Repository
public interface MemberFilterRepository extends JpaRepository<MemberFilter, MemberFilterId> {
    
    /**
     * 필터 ID로 회원-필터 관계 목록 조회
     * 🔍 특정 필터를 사용하는 모든 회원 관계 조회
     */
    List<MemberFilter> findByFilterId(Long filterId);
    
    /**
     * 회원 ID로 회원-필터 관계 목록 조회
     * 🔍 특정 회원이 접근 가능한 모든 필터 관계 조회
     */
    List<MemberFilter> findByMemberId(Long memberId);
    
    /**
     * 회원-필터 관계 존재 여부 확인
     * ✅ 특정 회원과 필터 간의 관계가 존재하는지 확인
     */
    boolean existsByMemberIdAndFilterId(Long memberId, Long filterId);
} 