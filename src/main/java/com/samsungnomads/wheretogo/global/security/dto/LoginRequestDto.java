package com.samsungnomads.wheretogo.global.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRequestDto {
    private String loginIdOrEmail;
    private String password;
}
