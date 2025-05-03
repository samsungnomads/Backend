package com.samsungnomads.wheretogo.global.security.service;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import com.samsungnomads.wheretogo.global.security.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String input) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        boolean isEmail = input.contains("@");

        Member member = isEmail
                ? memberRepository.findByEmail(input)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다"))
                : memberRepository.findByLoginId(input)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다"));

        return createUserDetails(member, input);
    }

    private UserDetailsImpl createUserDetails(Member member, String input) {

        return UserDetailsImpl.builder()
                .username(input)
                .password(member.getPassword())
                .authorities(member.getRoles())
                .build();
    }
}
