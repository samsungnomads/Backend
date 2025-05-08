package com.samsungnomads.wheretogo.domain.member.controller;

import com.samsungnomads.wheretogo.domain.member.dto.MemberCreateRequest;
import com.samsungnomads.wheretogo.domain.member.dto.MemberIdResponse;
import com.samsungnomads.wheretogo.domain.member.dto.MemberResponse;
import com.samsungnomads.wheretogo.domain.member.dto.MemberUpdateRequest;
import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.service.MemberService;
import com.samsungnomads.wheretogo.global.error.ErrorCode;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import com.samsungnomads.wheretogo.global.success.SuccessCode;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 컨트롤러
 * 👤 회원 관련 API 요청을 처리하는 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;
    /**
     * 현재 로그인한 회원 정보 조회
     * 🔍 현재 로그인한 회원의 정보를 조회합니다.
     */
    @GetMapping()
    @Override
    public ResponseEntity<SuccessResponse<MemberResponse>> getMyInfo(@AuthenticationPrincipal Member member) {
        // 🔐 현재 인증된 회원 정보를 AuthenticationPrincipal을 통해 직접 가져옵니다
        return SuccessResponse.of(SuccessCode.OK, MemberResponse.of(member));
    }
    
    /**
     * 현재 로그인한 회원 정보 수정
     * 🔄 현재 로그인한 회원의 정보를 수정합니다.
     */
    @PutMapping()
    @Override
    public ResponseEntity<SuccessResponse> updateMyInfo(
            @AuthenticationPrincipal Member member,
            @Valid @RequestBody MemberUpdateRequest request) {
        // 🔐 현재 인증된 회원의 ID를 사용하여 정보를 업데이트합니다
        memberService.update(member.getId(), request.getPassword(), request.getNickname());
        return SuccessResponse.of(SuccessCode.UPDATED);
    }

    
    /**
     * 현재 로그인한 회원 삭제
     * 🗑️ 현재 로그인한 회원을 삭제합니다.
     */
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<SuccessResponse> deleteMyAccount(@AuthenticationPrincipal Member member) {
        // 🔐 현재 인증된 회원의 ID를 사용하여 계정을 삭제합니다
        memberService.delete(member.getId());
        return SuccessResponse.of(SuccessCode.DELETED);
    }
}
