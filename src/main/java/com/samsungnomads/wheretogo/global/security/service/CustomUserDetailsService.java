package com.samsungnomads.wheretogo.global.security.service;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        log.info("loadUserByUsername");
        boolean isEmail = input.contains("@");

        Member member = isEmail
                ? memberRepository.findByEmail(input)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다ㅎㅎ"))
                : memberRepository.findByLoginId(input)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다ㅎㅎ"));

        return createUserDetails(member, input);
    }

    private UserDetails createUserDetails(Member member, String input) {

        return User.builder()
                .username(input)
                .password(member.getPassword())
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }
}
