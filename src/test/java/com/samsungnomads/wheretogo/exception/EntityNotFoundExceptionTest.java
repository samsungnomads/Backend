package com.samsungnomads.wheretogo.exception;

import com.samsungnomads.wheretogo.global.error.ErrorCode;
import com.samsungnomads.wheretogo.global.error.exception.BusinessException;
import com.samsungnomads.wheretogo.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * EntityNotFoundException 클래스 테스트
 * 🧪 엔티티 조회 실패 예외의 생성과 정보 접근을 검증
 */
class EntityNotFoundExceptionTest {

    @Test
    @DisplayName("메시지로 EntityNotFoundException을 생성할 수 있다")
    void createWithMessage() {
        // given
        String message = "아이디가 'test123'인 회원을 찾을 수 없습니다";

        // when
        EntityNotFoundException exception = new EntityNotFoundException(message);

        // then
        assertEquals(ErrorCode.ENTITY_NOT_FOUND, exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("ErrorCode로 EntityNotFoundException을 생성할 수 있다")
    void createWithErrorCode() {
        // given
        ErrorCode errorCode = ErrorCode.FILTER_NOT_FOUND;

        // when
        EntityNotFoundException exception = new EntityNotFoundException(errorCode);

        // then
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorCode.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("memberNotFound 정적 메서드로 회원 조회 실패 예외를 생성할 수 있다")
    void createMemberNotFoundException() {
        // given
        String memberId = "user123";

        // when
        EntityNotFoundException exception = EntityNotFoundException.memberNotFound(memberId);

        // then
        assertEquals(ErrorCode.MEMBER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("filterNotFound 정적 메서드로 필터 조회 실패 예외를 생성할 수 있다")
    void createFilterNotFoundException() {
        // given
        Integer filterId = 42;

        // when
        EntityNotFoundException exception = EntityNotFoundException.filterNotFound(filterId);

        // then
        assertEquals(ErrorCode.FILTER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("stationNotFound 정적 메서드로 역 조회 실패 예외를 생성할 수 있다")
    void createStationNotFoundException() {
        // given
        String stationName = "강남역";

        // when
        EntityNotFoundException exception = EntityNotFoundException.stationNotFound(stationName);

        // then
        assertEquals(ErrorCode.STATION_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("EntityNotFoundException은 BusinessException을 상속한다")
    void shouldExtendBusinessException() {
        // given
        EntityNotFoundException exception = new EntityNotFoundException("엔티티를 찾을 수 없습니다");

        // when & then
        assertInstanceOf(BusinessException.class, exception);
    }
} 