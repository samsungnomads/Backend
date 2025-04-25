package com.samsungnomads.wheretogo.domain.member.controller;

import com.samsungnomads.wheretogo.domain.member.dto.MemberCreateRequest;
import com.samsungnomads.wheretogo.domain.member.dto.MemberResponse;
import com.samsungnomads.wheretogo.domain.member.dto.MemberUpdateRequest;
import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.service.MemberService;
import com.samsungnomads.wheretogo.global.common.ApiResponse;
import com.samsungnomads.wheretogo.global.error.ErrorCode;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 컨트롤러
 * 👤 회원 관련 API 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    /**
     * 회원 목록 조회
     * 🔍 모든 회원 목록을 반환합니다.
     */
    @GetMapping
    @Override
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAllMembers() {
        List<Member> members = memberService.findMembers();
        List<MemberResponse> responses = members.stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    /**
     * 회원 상세 조회
     * 🔍 특정 회원의 상세 정보를 조회합니다.
     */
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(@PathVariable Long id) {
        Member member = memberService.findOne(id);
        return ResponseEntity.ok(ApiResponse.success(MemberResponse.of(member)));
    }

    /**
     * 회원 등록
     * 📝 새로운 회원을 등록합니다.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<ApiResponse<Long>> createMember(@Valid @RequestBody MemberCreateRequest request) {
        // 아이디 중복 검사
        if (!memberService.isLoginIdAvailable(request.getLoginId())) {
            throw new BusinessException(ErrorCode.MEMBER_ID_DUPLICATION, 
                    String.format("이미 사용 중인 아이디입니다: %s", request.getLoginId()));
        }
        
        // 이메일 중복 검사
        if (!memberService.isEmailAvailable(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATION, 
                    String.format("이미 사용 중인 이메일입니다: %s", request.getEmail()));
        }
        
        Member member = request.toEntity();
        Long id = memberService.join(member);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, id));
    }

    /**
     * 회원 정보 수정
     * 🔄 기존 회원 정보를 수정합니다.
     */
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ApiResponse<Void>> updateMember(
            @PathVariable Long id, 
            @Valid @RequestBody MemberUpdateRequest request) {
        memberService.update(id, request.getPassword(), request.getNickname());
        return ResponseEntity.ok(ApiResponse.success());
    }

    /**
     * 회원 삭제
     * 🗑️ 기존 회원을 삭제합니다.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<ApiResponse<Void>> deleteMember(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success(HttpStatus.NO_CONTENT, null));
    }
}
