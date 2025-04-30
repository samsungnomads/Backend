package com.samsungnomads.wheretogo.domain.station.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationDto {

    String name;
    String address;
    String phone;
    String url;
    Double rating;
}
