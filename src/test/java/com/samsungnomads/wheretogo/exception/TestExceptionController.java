package com.samsungnomads.wheretogo.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 테스트용 컨트롤러
 * 🧪 다양한 예외 상황을 발생시키는 API 제공
 */
@RestController
@RequestMapping("/test")
public class TestExceptionController {
    
    /**
     * 비즈니스 예외 발생 엔드포인트
     * 💼 INVALID_INPUT_VALUE 예외 발생
     */
    @GetMapping("/business-exception")
    public String throwBusinessException() {
        throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
    }
    
    /**
     * 엔티티 조회 실패 예외 발생 엔드포인트
     * 👤 회원 조회 실패 예외 발생
     */
    @GetMapping("/entity-not-found/{id}")
    public String throwEntityNotFoundException(@PathVariable String id) {
        throw EntityNotFoundException.memberNotFound(id);
    }
    
    /**
     * 처리되지 않은 예외 발생 엔드포인트
     * 💥 런타임 예외 발생
     */
    @GetMapping("/unexpected-exception")
    public String throwUnexpectedException() {
        throw new RuntimeException("예상치 못한 오류 발생");
    }
} 