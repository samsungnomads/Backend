package com.samsungnomads.wheretogo.global.error.exception;

import com.samsungnomads.wheretogo.global.error.ErrorCode;

/**
 * 엔티티를 찾을 수 없을 때 발생하는 예외
 * 🔍 데이터베이스에서 찾을 수 없는 엔티티에 접근 시 발생
 */
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(ErrorCode.ENTITY_NOT_FOUND, message);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
    
    public EntityNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 회원을 찾을 수 없을 때 발생
     * 👤 회원 ID로 조회 실패 시 사용
     */
    public static EntityNotFoundException memberNotFound(String memberId) {
        return new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND, 
                String.format("회원을 찾을 수 없습니다. ID: %s", memberId));
    }

    /**
     * 필터를 찾을 수 없을 때 발생
     * 🔍 필터 ID로 조회 실패 시 사용
     */
    public static EntityNotFoundException filterNotFound(Integer filterId) {
        return new EntityNotFoundException(ErrorCode.FILTER_NOT_FOUND, 
                String.format("필터를 찾을 수 없습니다. ID: %d", filterId));
    }

    /**
     * 역을 찾을 수 없을 때 발생
     * 🚉 역 ID 또는 역 이름으로 조회 실패 시 사용
     */
    public static EntityNotFoundException stationNotFound(String stationNameOrId) {
        return new EntityNotFoundException(ErrorCode.STATION_NOT_FOUND, 
                String.format("지하철역을 찾을 수 없습니다. 이름/ID: %s", stationNameOrId));
    }
} 