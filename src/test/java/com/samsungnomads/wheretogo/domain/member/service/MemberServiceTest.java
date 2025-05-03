package com.samsungnomads.wheretogo.domain.member.service;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import com.samsungnomads.wheretogo.global.error.exception.EntityNotFoundException;
import com.samsungnomads.wheretogo.global.security.jwt.JwtToken;
import com.samsungnomads.wheretogo.global.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 회원 서비스 테스트 클래스 🧪
 * MemberService의 모든 기능을 테스트합니다.
 */
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member testMember;

    @BeforeEach
    void setUp() {
        // 테스트용 회원 데이터 설정 👤
        testMember = Member.builder()
                .id(1L)
                .loginId("testuser")
                .email("test@example.com")
                .password("password123")
                .nickname("테스터")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        // 테스트용 인증 객체 생성 🔑
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testMember.getLoginId(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // 보안 컨텍스트에 인증 정보 설정 🔒
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("회원 가입 성공 테스트 ✅")
    void joinSuccess() {
        // Given: 회원 정보가 주어졌을 때
        when(memberRepository.existsByLoginId(anyString())).thenReturn(false);
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);

        // When: 회원 가입을 시도하면
        Long savedId = memberService.join(testMember);

        // Then: 회원 ID가 반환되어야 함
        assertEquals(testMember.getId(), savedId);
        verify(memberRepository).existsByLoginId(testMember.getLoginId());
        verify(memberRepository).existsByEmail(testMember.getEmail());
        verify(memberRepository).save(testMember);
    }

    @Test
    @DisplayName("중복 아이디 회원 가입 실패 테스트 ❌")
    void joinFailWithDuplicateLoginId() {
        // Given: 중복된 아이디가 있을 때
        when(memberRepository.existsByLoginId(anyString())).thenReturn(true);

        // When & Then: 회원 가입을 시도하면 예외가 발생해야 함
        assertThrows(BusinessException.class, () -> memberService.join(testMember));
        verify(memberRepository).existsByLoginId(testMember.getLoginId());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("중복 이메일 회원 가입 실패 테스트 ❌")
    void joinFailWithDuplicateEmail() {
        // Given: 중복된 이메일이 있을 때
        when(memberRepository.existsByLoginId(anyString())).thenReturn(false);
        when(memberRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then: 회원 가입을 시도하면 예외가 발생해야 함
        assertThrows(BusinessException.class, () -> memberService.join(testMember));
        verify(memberRepository).existsByLoginId(testMember.getLoginId());
        verify(memberRepository).existsByEmail(testMember.getEmail());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("모든 회원 조회 테스트 📋")
    void findMembersTest() {
        // Given: 회원 목록이 있을 때
        List<Member> members = new ArrayList<>();
        members.add(testMember);
        members.add(Member.builder()
                .id(2L)
                .loginId("user2")
                .email("user2@example.com")
                .password("password456")
                .nickname("사용자2")
                .build());

        when(memberRepository.findAll()).thenReturn(members);

        // When: 모든 회원을 조회하면
        List<Member> foundMembers = memberService.findMembers();

        // Then: 회원 목록이 반환되어야 함
        assertEquals(2, foundMembers.size());
        verify(memberRepository).findAll();
    }

    @Test
    @DisplayName("ID로 회원 조회 성공 테스트 🔍")
    void findOneByIdSuccess() {
        // Given: 회원이 존재할 때
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

        // When: ID로 회원을 조회하면
        Member foundMember = memberService.findOne(1L);

        // Then: 회원이 반환되어야 함
        assertNotNull(foundMember);
        assertEquals(testMember.getId(), foundMember.getId());
        verify(memberRepository).findById(1L);
    }

    @Test
    @DisplayName("ID로 회원 조회 실패 테스트 ❌")
    void findOneByIdFail() {
        // Given: 회원이 존재하지 않을 때
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then: ID로 회원을 조회하면 예외가 발생해야 함
        assertThrows(EntityNotFoundException.class, () -> memberService.findOne(99L));
        verify(memberRepository).findById(99L);
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 성공 테스트 🔍")
    void findByLoginIdSuccess() {
        // Given: 회원이 존재할 때
        when(memberRepository.findByLoginId("testuser")).thenReturn(Optional.of(testMember));

        // When: 로그인 아이디로 회원을 조회하면
        Member foundMember = memberService.findByLoginId("testuser");

        // Then: 회원이 반환되어야 함
        assertNotNull(foundMember);
        assertEquals(testMember.getLoginId(), foundMember.getLoginId());
        verify(memberRepository).findByLoginId("testuser");
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 실패 테스트 ❌")
    void findByLoginIdFail() {
        // Given: 회원이 존재하지 않을 때
        when(memberRepository.findByLoginId("nonexistent")).thenReturn(Optional.empty());

        // When & Then: 로그인 아이디로 회원을 조회하면 예외가 발생해야 함
        assertThrows(EntityNotFoundException.class, () -> memberService.findByLoginId("nonexistent"));
        verify(memberRepository).findByLoginId("nonexistent");
    }

    @Test
    @DisplayName("이메일로 회원 조회 성공 테스트 🔍")
    void findByEmailSuccess() {
        // Given: 회원이 존재할 때
        when(memberRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testMember));

        // When: 이메일로 회원을 조회하면
        Member foundMember = memberService.findByEmail("test@example.com");

        // Then: 회원이 반환되어야 함
        assertNotNull(foundMember);
        assertEquals(testMember.getEmail(), foundMember.getEmail());
        verify(memberRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("이메일로 회원 조회 실패 테스트 ❌")
    void findByEmailFail() {
        // Given: 회원이 존재하지 않을 때
        when(memberRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When & Then: 이메일로 회원을 조회하면 예외가 발생해야 함
        assertThrows(EntityNotFoundException.class, () -> memberService.findByEmail("nonexistent@example.com"));
        verify(memberRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    @DisplayName("회원 정보 업데이트 테스트 🔄")
    void updateMemberTest() {
        // Given: 회원이 존재할 때
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

        // When: 회원 정보를 업데이트하면
        memberService.update(1L, "newpassword", "새닉네임");

        // Then: 회원 정보가 업데이트되어야 함
        assertEquals("newpassword", testMember.getPassword());
        assertEquals("새닉네임", testMember.getNickname());
        verify(memberRepository).findById(1L);
    }

    @Test
    @DisplayName("회원 삭제 성공 테스트 🗑️")
    void deleteMemberSuccess() {
        // Given: 회원이 존재할 때
        when(memberRepository.existsById(1L)).thenReturn(true);
        doNothing().when(memberRepository).deleteById(1L);

        // When: 회원을 삭제하면
        memberService.delete(1L);

        // Then: deleteById 메서드가 호출되어야 함
        verify(memberRepository).existsById(1L);
        verify(memberRepository).deleteById(1L);
    }

    @Test
    @DisplayName("회원 삭제 실패 테스트 ❌")
    void deleteMemberFail() {
        // Given: 회원이 존재하지 않을 때
        when(memberRepository.existsById(99L)).thenReturn(false);

        // When & Then: 회원을 삭제하면 예외가 발생해야 함
        assertThrows(EntityNotFoundException.class, () -> memberService.delete(99L));
        verify(memberRepository).existsById(99L);
        verify(memberRepository, never()).deleteById(99L);
    }

    @Test
    @DisplayName("아이디 중복 검사 테스트 ✅")
    void isLoginIdAvailableTest() {
        // Given: 중복된 아이디가 있을 때 / 없을 때
        when(memberRepository.existsByLoginId("existing")).thenReturn(true);
        when(memberRepository.existsByLoginId("new")).thenReturn(false);

        // When & Then: 아이디 중복 검사 결과가 올바르게 반환되어야 함
        assertTrue(memberService.isLoginIdAvailable("existing"));
        assertFalse(memberService.isLoginIdAvailable("new"));
        verify(memberRepository).existsByLoginId("existing");
        verify(memberRepository).existsByLoginId("new");
    }

    @Test
    @DisplayName("이메일 중복 검사 테스트 ✅")
    void isEmailAvailableTest() {
        // Given: 중복된 이메일이 있을 때 / 없을 때
        when(memberRepository.existsByEmail("existing@example.com")).thenReturn(true);
        when(memberRepository.existsByEmail("new@example.com")).thenReturn(false);

        // When & Then: 이메일 중복 검사 결과가 올바르게 반환되어야 함
        assertTrue(memberService.isEmailAvailable("existing@example.com"));
        assertFalse(memberService.isEmailAvailable("new@example.com"));
        verify(memberRepository).existsByEmail("existing@example.com");
        verify(memberRepository).existsByEmail("new@example.com");
    }
} 