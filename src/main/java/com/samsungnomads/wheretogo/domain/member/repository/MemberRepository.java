package com.samsungnomads.wheretogo.domain.member.repository;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 회원 리포지토리
 * 💾 회원 정보에 접근하는 데이터 액세스 계층
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    /**
     * ID(PK)로 회원 조회
     * 🔑 데이터베이스 기본키로 회원 정보 조회
     */
    Optional<Member> findById(Long id);
    
    /**
     * ID(PK) 존재 여부 확인
     * 🔑 기본키 기준으로 회원 존재 여부 확인
     */
    boolean existsById(Long id);
    
    /**
     * ID(PK)로 회원 삭제
     * 🗑️ 기본키 기준으로 회원 삭제
     */
    void deleteById(Long id);
    
    /**
     * ID(PK) 목록으로 회원 조회
     * 🔍 여러 기본키로 회원 목록 조회
     */
    List<Member> findAllByIdIn(List<Long> ids);
    
    /**
     * 모든 회원 조회
     * 📋 전체 회원 목록 조회
     */
    List<Member> findAll();
    
    /**
     * 로그인 아이디로 회원 조회
     * 🔍 고유한 회원 로그인 아이디로 회원 정보 조회
     */
    Optional<Member> findByLoginId(String loginId);
    
    /**
     * 이메일로 회원 조회
     * 🔍 이메일 주소로 회원 정보 조회
     */
    Optional<Member> findByEmail(String email);
    
    /**
     * 로그인 아이디 존재 여부 확인
     * ✅ 특정 로그인 아이디가 이미 사용 중인지 확인
     */
    boolean existsByLoginId(String loginId);
    
    /**
     * 이메일 존재 여부 확인
     * ✅ 특정 이메일이 이미 사용 중인지 확인
     */
    boolean existsByEmail(String email);
}
