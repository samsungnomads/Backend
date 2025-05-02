package com.samsungnomads.wheretogo.domain.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AuthTestResponseDto {

    @JsonProperty
    String username;

    @JsonProperty
    List<String> authorities;
}
