package com.samsungnomads.wheretogo.domain.station.service;

import com.samsungnomads.wheretogo.domain.line.Line;
import com.samsungnomads.wheretogo.domain.line.LineRepository;
import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import com.samsungnomads.wheretogo.domain.relationship.repository.LineStationRepository;
import com.samsungnomads.wheretogo.domain.station.dto.StationResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StationService {
    private final LineRepository lineRepository;
    private final LineStationRepository lineStationRepository;

    @Transactional
    public Map<String, List<StationResponseDto>> getAllStationsGroupedByLine() {

        Map<String, List<StationResponseDto>> stationsGroupedByLine = new HashMap<>();
        List<Line> lines = lineRepository.findAll();

        for (Line line : lines) {

            List<LineStation> lineStations = lineStationRepository.findByLineOrderByOrderInLineAsc(line);

            for (LineStation lineStation : lineStations) {
                stationsGroupedByLine.computeIfAbsent(lineStation.getLine().getName(), k -> new ArrayList<>())
                        .add(new StationResponseDto(
                                lineStation.getStation().getName(),
                                lineStation.getOrderInLine(),
                                lineStation.getStation().getAddress(),
                                lineStation.getStation().getArea()
                        ));
            }
        }
        return stationsGroupedByLine;
    }
}
