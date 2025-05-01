package com.samsungnomads.wheretogo.global.initializer;

import com.samsungnomads.wheretogo.domain.line.Line;
import com.samsungnomads.wheretogo.domain.line.LineRepository;
import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import com.samsungnomads.wheretogo.domain.relationship.repository.LineStationRepository;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import com.samsungnomads.wheretogo.domain.station.entity.enums.LineCode;
import com.samsungnomads.wheretogo.domain.station.repository.StationRepository;
import com.samsungnomads.wheretogo.external.KricApiResponseDto;
import com.samsungnomads.wheretogo.external.KricApiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class StationDataInitializer {

    private final StationRepository stationRepository;
    private final LineRepository lineRepository;
    private final LineStationRepository lineStationRepository;
    private final KricApiService kricApiService;

    @Transactional
    public Boolean initialize() {

        Map<String, Station> stationMap = new HashMap<>();

        for (LineCode lineCode : LineCode.values()) {
            Line line = new Line(lineCode);

            List<KricApiResponseDto.StationInfo> stationInfoList = kricApiService.fetchStationInfoList(line);

            // 외부 API 통신이 안되는 경우
            if (stationInfoList == null) {
                return false;
            }

            // 외부 API 통신은 되지만, 호선별 정보를 받아올 수 없는 경우
            if (stationInfoList.isEmpty()) {
                log.info("No stations found for line code: line-name : {} line-code: {}", lineCode.getName(), line.getCode());
                continue;
            }

            initializeLine(line);

            initializeStations(stationInfoList, stationMap);

            initializeLineStations(stationInfoList, line, stationMap);


        }

        return true;

    }


    private void initializeLine(Line line) {
        lineRepository.save(line);
    }

    private void initializeStations(List<KricApiResponseDto.StationInfo> stationInfoList, Map<String, Station> stationMap) {

        for (KricApiResponseDto.StationInfo stationInfo : stationInfoList) {

            Station station = stationMap.get(stationInfo.getStinNm());

            if (station == null) {
                station = new Station(stationInfo.getStinNm(), "", stationInfo.getMreaWideCd());
                stationRepository.save(station);
                stationMap.put(stationInfo.getStinNm(), station);
            }

        }
    }

    private void initializeLineStations(List<KricApiResponseDto.StationInfo> stationInfoList, Line line, Map<String, Station> stationMap) {

        for (KricApiResponseDto.StationInfo stationInfo : stationInfoList) {

            Station station = stationMap.get(stationInfo.getStinNm());

            LineStation lineStation = LineStation.builder()
                    .line(line)
                    .station(station)
                    .orderInLine(stationInfo.getStinConsOrdr())
                    .build();
            lineStationRepository.save(lineStation);
        }
    }
}
