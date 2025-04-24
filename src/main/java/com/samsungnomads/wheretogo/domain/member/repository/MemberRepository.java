package com.samsungnomads.wheretogo.domain.member.repository;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 회원 리포지토리
 * 💾 회원 정보에 접근하는 데이터 액세스 계층
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    /**
     * 아이디로 회원 조회
     * 🔍 고유한 회원 아이디로 회원 정보 조회
     */
    Optional<Member> findById(String id);
    
    /**
     * 이메일로 회원 조회
     * 🔍 이메일 주소로 회원 정보 조회
     */
    Optional<Member> findByEmail(String email);
    
    /**
     * 아이디 존재 여부 확인
     * ✅ 특정 아이디가 이미 사용 중인지 확인
     */
    boolean existsById(String id);
    
    /**
     * 이메일 존재 여부 확인
     * ✅ 특정 이메일이 이미 사용 중인지 확인
     */
    boolean existsByEmail(String email);
}
