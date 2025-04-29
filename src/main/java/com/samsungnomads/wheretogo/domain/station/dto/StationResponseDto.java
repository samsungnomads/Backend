package com.samsungnomads.wheretogo.domain.station.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StationResponseDto {

    private String name;
    private Integer orderInLine;
    private String address;
    private String area;

}
