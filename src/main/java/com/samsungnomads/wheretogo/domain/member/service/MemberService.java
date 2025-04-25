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
        // 컨트롤러에서 중복 검사 진행하므로 여기서는 생략
        memberRepository.save(member);
        return member.getUid();
    }
    
    /**
     * 전체 회원 조회
     * 🔍 등록된 모든 회원 목록 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    
    /**
     * 회원 ID로 회원 조회
     * 🔍 고유 식별자로 특정 회원 정보 조회
     */
    public Member findOne(Long uid) {
        return memberRepository.findById(uid)
                .orElseThrow(() -> EntityNotFoundException.memberNotFound(uid.toString()));
    }
    
    /**
     * 회원 아이디로 회원 조회
     * 🔍 회원 아이디로 특정 회원 정보 조회
     */
    public Member findByMemberId(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.memberNotFound(id));
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
    public void update(Long uid, String password, String nickname) {
        Member member = findOne(uid);
        member.update(password, nickname);
    }
    
    /**
     * 회원 삭제
     * 🗑️ 회원 정보 삭제
     */
    @Transactional
    public void delete(Long uid) {
        if (!memberRepository.existsById(uid)) {
            throw EntityNotFoundException.memberNotFound(uid.toString());
        }
        memberRepository.deleteById(uid);
    }
    
    /**
     * 아이디 중복 검사
     * ✅ 아이디 사용 가능 여부 확인
     */
    public boolean isIdAvailable(String id) {
        return !memberRepository.existsById(id);
    }
    
    /**
     * 이메일 중복 검사
     * ✅ 이메일 사용 가능 여부 확인
     */
    public boolean isEmailAvailable(String email) {
        return !memberRepository.existsByEmail(email);
    }
}
