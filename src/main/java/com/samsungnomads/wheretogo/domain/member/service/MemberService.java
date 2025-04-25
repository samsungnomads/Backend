package com.samsungnomads.wheretogo.domain.member.service;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import com.samsungnomads.wheretogo.global.error.ErrorCode;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import com.samsungnomads.wheretogo.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 앞으로 해야 할 일
 * 1. 인증 기능 추가
 * 2. 함수 오버라이드하여 변수 값이 String 인지 int 인지 확인하여,
 * DB 조회 분기처리하기. 예시로 String 인 경우 LoginId 로 조회하고, int 인 경우 Id 로 조회하기.
 * 3. 이후 등등
 */


/**
 * 회원 서비스
 * 💼 회원 관리 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    
    /**
     * 회원 생성
     * 📝 신규 회원 정보 등록
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    
    /**
     * 회원 중복 검증
     * 🔍 가입 시 아이디와 이메일 중복 체크
     */
    private void validateDuplicateMember(Member member) {
        if (!isLoginIdAvailable(member.getLoginId())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATION, "이미 사용 중인 아이디입니다: " + member.getLoginId());
        }
        
        if (!isEmailAvailable(member.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATION, "이미 사용 중인 이메일입니다: " + member.getEmail());
        }
    }
    
    /**
     * 모든 회원 조회
     * 🔍 등록된 모든 회원 목록 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    
    /**
     * 회원 ID로 회원 조회
     * 🔍 고유 식별자로 특정 회원 정보 조회
     */
    public Member findOne(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.memberNotFound(id.toString()));
    }

    /**
     * 회원 로그인 아이디로 회원 조회
     * 🔍 회원 로그인 아이디로 특정 회원 정보 조회
     */
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> EntityNotFoundException.memberNotFound(loginId));
    }
    
    /**
     * 이메일로 회원 조회
     * 🔍 이메일 주소로 특정 회원 정보 조회
     */
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> EntityNotFoundException.memberNotFound(email));
    }
    
    /**
     * 회원 정보 업데이트
     * 🔄 기존 회원 정보 수정
     */
    @Transactional
    public void update(Long id, String password, String nickname) {
        Member member = findOne(id);
        member.update(password, nickname);
    }
    
    /**
     * 회원 삭제
     * 🗑️ 회원 정보 삭제
     */
    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw EntityNotFoundException.memberNotFound(id.toString());
        }
        memberRepository.deleteById(id);
    }
    
    /**
     * 아이디 중복 검사
     * ✅ 아이디 사용 가능 여부 확인
     */
    public boolean isLoginIdAvailable(String loginId) {
        return !memberRepository.existsByLoginId(loginId);
    }
    
    /**
     * 이메일 중복 검사
     * ✅ 이메일 사용 가능 여부 확인
     */
    public boolean isEmailAvailable(String email) {
        return !memberRepository.existsByEmail(email);
    }
}
