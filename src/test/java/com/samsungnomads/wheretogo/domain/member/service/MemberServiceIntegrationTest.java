package com.samsungnomads.wheretogo.domain.member.service;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import com.samsungnomads.wheretogo.global.error.exception.EntityNotFoundException;
import com.samsungnomads.wheretogo.global.security.jwt.JwtToken;
import com.samsungnomads.wheretogo.global.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 회원 서비스 통합 테스트 클래스 🔄
 * 실제 데이터베이스와 연동하여 통합 테스트를 수행합니다.
 */
@SpringBootTest
@ActiveProfiles("test") // 테스트 프로파일 사용
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Member testMember;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        // 테스트용 회원 데이터 설정 👤
        testMember = Member.builder()
                .loginId("integrationtest")
                .email("integration@example.com")
                .password(passwordEncoder.encode("password123"))
                .nickname("통합테스터")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        // 회원 저장
        memberRepository.save(testMember);

        // 테스트용 인증 객체 생성 🔑
        authentication = new UsernamePasswordAuthenticationToken(
                testMember.getLoginId(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // 보안 컨텍스트에 인증 정보 설정 🔒
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성 - 실제 테스트에서 사용할 경우에만 생성
        // 실제 사용되는 테스트에서만 토큰을 생성하도록 수정
    }

    @AfterEach
    void tearDown() {
        // 테스트 데이터 정리
        memberRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("회원 가입 통합 테스트 ✅")
    void joinIntegrationTest() {
        // Given: 새로운 회원 정보
        Member newMember = Member.builder()
                .loginId("newuser")
                .email("new@example.com")
                .password(passwordEncoder.encode("newpassword"))
                .nickname("새사용자")
                .build();

        // When: 회원 가입
        Long savedId = memberService.join(newMember);

        // Then: 회원이 저장되었는지 확인
        assertNotNull(savedId);
        Member found = memberRepository.findById(savedId).orElse(null);
        assertNotNull(found);
        assertEquals("newuser", found.getLoginId());
        assertEquals("new@example.com", found.getEmail());
    }

    @Test
    @DisplayName("중복 회원 가입 통합 테스트 ❌")
    void duplicateMemberJoinIntegrationTest() {
        // Given: 이미 존재하는 회원 정보와 동일한 정보
        Member duplicateMember = Member.builder()
                .loginId("integrationtest") // 이미 존재하는 아이디
                .email("duplicate@example.com")
                .password(passwordEncoder.encode("password123"))
                .nickname("중복테스터")
                .build();

        // When & Then: 회원 가입 시 예외 발생
        assertThrows(BusinessException.class, () -> memberService.join(duplicateMember));
    }

    @Test
    @DisplayName("회원 조회 통합 테스트 🔍")
    void findMemberIntegrationTest() {
        // Given: 저장된 회원 (setUp에서 생성)

        // When: 회원 ID로 조회
        Member foundMember = memberService.findOne(testMember.getId());

        // Then: 조회된 회원 정보 확인
        assertNotNull(foundMember);
        assertEquals(testMember.getId(), foundMember.getId());
        assertEquals(testMember.getLoginId(), foundMember.getLoginId());
        assertEquals(testMember.getEmail(), foundMember.getEmail());
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 통합 테스트 🔍")
    void findByLoginIdIntegrationTest() {
        // Given: 저장된 회원 (setUp에서 생성)

        // When: 로그인 아이디로 회원 조회
        Member foundMember = memberService.findByLoginId(testMember.getLoginId());

        // Then: 조회된 회원 정보 확인
        assertNotNull(foundMember);
        assertEquals(testMember.getId(), foundMember.getId());
        assertEquals(testMember.getLoginId(), foundMember.getLoginId());
    }

    @Test
    @DisplayName("이메일로 회원 조회 통합 테스트 🔍")
    void findByEmailIntegrationTest() {
        // Given: 저장된 회원 (setUp에서 생성)

        // When: 이메일로 회원 조회
        Member foundMember = memberService.findByEmail(testMember.getEmail());

        // Then: 조회된 회원 정보 확인
        assertNotNull(foundMember);
        assertEquals(testMember.getId(), foundMember.getId());
        assertEquals(testMember.getEmail(), foundMember.getEmail());
    }

    @Test
    @DisplayName("모든 회원 조회 통합 테스트 📋")
    void findAllMembersIntegrationTest() {
        // Given: 추가 회원 생성
        Member secondMember = Member.builder()
                .loginId("seconduser")
                .email("second@example.com")
                .password(passwordEncoder.encode("password456"))
                .nickname("두번째사용자")
                .build();
        memberRepository.save(secondMember);

        // When: 모든 회원 조회
        List<Member> members = memberService.findMembers();

        // Then: 회원 목록 확인
        assertNotNull(members);
        assertTrue(members.size() >= 2); // 최소 2명 이상 (기존 + 추가)
        assertTrue(members.stream().anyMatch(m -> m.getLoginId().equals("integrationtest")));
        assertTrue(members.stream().anyMatch(m -> m.getLoginId().equals("seconduser")));
    }

    @Test
    @DisplayName("회원 정보 업데이트 통합 테스트 🔄")
    void updateMemberIntegrationTest() {
        // Given: 저장된 회원 (setUp에서 생성)
        String newPassword = passwordEncoder.encode("updatedpassword");
        String newNickname = "업데이트된닉네임";

        // When: 회원 정보 업데이트
        memberService.update(testMember.getId(), newPassword, newNickname);

        // Then: 업데이트된 회원 정보 확인
        Member updatedMember = memberRepository.findById(testMember.getId()).orElse(null);
        assertNotNull(updatedMember);
        assertEquals(newPassword, updatedMember.getPassword());
        assertEquals(newNickname, updatedMember.getNickname());
    }

    @Test
    @DisplayName("회원 삭제 통합 테스트 🗑️")
    void deleteMemberIntegrationTest() {
        // Given: 저장된 회원 (setUp에서 생성)

        // When: 회원 삭제
        memberService.delete(testMember.getId());

        // Then: 회원이 삭제되었는지 확인
        assertThrows(EntityNotFoundException.class, () -> memberService.findOne(testMember.getId()));
        assertFalse(memberRepository.existsById(testMember.getId()));
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회 통합 테스트 ❌")
    void findNonExistentMemberIntegrationTest() {
        // Given: 존재하지 않는 회원 ID
        Long nonExistentId = 9999L;

        // When & Then: 존재하지 않는 회원 조회 시 예외 발생
        assertThrows(EntityNotFoundException.class, () -> memberService.findOne(nonExistentId));
    }

    @Test
    @DisplayName("아이디 중복 검사 통합 테스트 ✅")
    void isLoginIdAvailableIntegrationTest() {
        // Given: 저장된 회원 (setUp에서 생성)

        // When & Then: 아이디 중복 검사
        assertTrue(memberService.isLoginIdAvailable(testMember.getLoginId())); // 존재하는 아이디
        assertFalse(memberService.isLoginIdAvailable("nonexistentuser")); // 존재하지 않는 아이디
    }

    @Test
    @DisplayName("이메일 중복 검사 통합 테스트 ✅")
    void isEmailAvailableIntegrationTest() {
        // Given: 저장된 회원 (setUp에서 생성)

        // When & Then: 이메일 중복 검사
        assertTrue(memberService.isEmailAvailable(testMember.getEmail())); // 존재하는 이메일
        assertFalse(memberService.isEmailAvailable("nonexistent@example.com")); // 존재하지 않는 이메일
    }

    @Test
    @DisplayName("JWT 토큰으로 인증된 상태에서 멤버 조회 테스트 🔐")
    void authenticatedMemberOperationsTest() {
        // Given: JWT 토큰 생성 (실제로 사용하는 테스트에서만 생성)
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        
        // When: 현재 인증된 회원의 정보로 회원 조회
        String currentLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Member authenticatedMember = memberService.findByLoginId(currentLoginId);
        
        // Then: 인증된 회원 정보 확인
        assertNotNull(authenticatedMember);
        assertEquals(testMember.getLoginId(), authenticatedMember.getLoginId());
        assertEquals(testMember.getId(), authenticatedMember.getId());
        
        // 추가: JWT 토큰의 유효성 확인
        assertNotNull(jwtToken);
        assertTrue(jwtTokenProvider.validateToken(jwtToken.getAccessToken()));
    }
} 