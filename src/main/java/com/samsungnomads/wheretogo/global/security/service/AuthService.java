package com.samsungnomads.wheretogo.global.security.service;

import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import com.samsungnomads.wheretogo.global.error.ErrorCode;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import com.samsungnomads.wheretogo.global.security.dto.LoginRequestDto;
import com.samsungnomads.wheretogo.global.security.dto.SignUpRequestDto;
import com.samsungnomads.wheretogo.global.security.jwt.JwtToken;
import com.samsungnomads.wheretogo.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignUpRequestDto signupRequestDto) {
        String loginId = signupRequestDto.getLoginId();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();

        if (memberRepository.existsByLoginId(loginId)) {
            throw new BusinessException(ErrorCode.MEMBER_ID_DUPLICATION);
        }

        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATION);
        }

        String encodedPassword = passwordEncoder.encode(password);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        memberRepository.save(signupRequestDto.toEntity(encodedPassword, roles));
    }

    public JwtToken login(LoginRequestDto loginRequestDto) {
        String loginIdOrEmail = loginRequestDto.getLoginIdOrEmail();
        String password = loginRequestDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginIdOrEmail, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);

    }


}
