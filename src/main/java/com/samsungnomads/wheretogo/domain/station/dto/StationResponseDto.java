package com.samsungnomads.wheretogo.domain.station.dto;

import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StationResponseDto {

    private String name;
    private Integer orderInLine;
    private String address;
    private String area;

    public static StationResponseDto from(Station station, LineStation lineStation) {
        return new StationResponseDto(
                station.getName(),
                lineStation.getOrderInLine(),
                station.getAddress(),
                station.getArea()
        );
    }
}
