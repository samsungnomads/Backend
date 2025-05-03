package com.samsungnomads.wheretogo.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    OK(HttpStatus.OK, "요청이 성공했습니다."),
    // 리소스 생성 성공
    CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다."),
    // 리소스 수정 성공
    UPDATED(HttpStatus.OK, "리소스가 성공적으로 수정되었습니다."),
    // 리소스 삭제 성공
    DELETED(HttpStatus.NO_CONTENT, "리소스가 성공적으로 삭제되었습니다."),

    // 회원 관련
    MEMBER_SIGNUP_SUCCESS(HttpStatus.OK, "회원가입이 성공적으로 완료되었습니다."),
    MEMBER_LOGIN_SUCCESS(HttpStatus.OK, "로그인이 성공적으로 완료되었습니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
