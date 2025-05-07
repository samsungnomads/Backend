package com.samsungnomads.wheretogo.domain.filter.dto;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.line.Line;
import com.samsungnomads.wheretogo.domain.relationship.entity.FilterStation;
import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import com.samsungnomads.wheretogo.domain.station.dto.StationResponseDto;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class FilterDetailDto {
    private Long id;
    private String name;
    private Boolean isShared;
    private String creatorNickname;
    private Map<String, List<StationResponseDto>> stationResponses;

    public static FilterDetailDto from(Filter filter) {
        Map<String, List<StationResponseDto>> stationMap = new HashMap<>();

        for (FilterStation filterStation : filter.getStations()) {
            Station station = filterStation.getStation();

            for (LineStation lineStation : station.getLineStations()) {
                Line line = lineStation.getLine();
                String lineName = line.getName();

                stationMap.computeIfAbsent(lineName, k -> new ArrayList<>())
                        .add(StationResponseDto.from(station, lineStation));
            }
        }

        return FilterDetailDto.builder()
                .id(filter.getId())
                .name(filter.getName())
                .isShared(filter.getIsShared())
                .creatorNickname(filter.getCreator().getNickname())
                .stationResponses(stationMap)
                .build();
    }
}
