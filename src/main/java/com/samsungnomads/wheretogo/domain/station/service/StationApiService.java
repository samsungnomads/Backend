package com.samsungnomads.wheretogo.domain.station.service;

import com.samsungnomads.wheretogo.domain.line.Line;
import com.samsungnomads.wheretogo.domain.line.LineRepository;
import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import com.samsungnomads.wheretogo.domain.relationship.repository.LineStationRepository;
import com.samsungnomads.wheretogo.domain.station.dto.PublicApiResponseDto;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import com.samsungnomads.wheretogo.domain.station.entity.enums.LineCode;
import com.samsungnomads.wheretogo.domain.station.repository.StationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StationApiService {
    private final RestTemplate restTemplate;
    private final StationRepository stationRepository;
    private final LineRepository lineRepository;
    private final LineStationRepository lineStationRepository;

    @PostConstruct
    public void init() {
        if (stationRepository.count() == 0) {
            fetchAndSaveStationRelations();
        }
    }

    private void fetchAndSaveStationRelations() {

        Map<String, Station> stationMap = new HashMap<>();

        for (LineCode lineCode : LineCode.values()) {
            // 1. Line Entity 저장
            Line line = saveLine(lineCode);

            //2. 공공 API 호출해서 데이터 가져오기
            List<PublicApiResponseDto.StationInfo> stationInfoList = fetchStationInfoList(lineCode.getCode());

            for (PublicApiResponseDto.StationInfo stationInfo : stationInfoList) {

                // 3. Station Entity 저장
                Station station = saveStation(stationInfo, stationMap);

                // 4. LineStation Entity 저장
                saveLineStation(stationInfo, line, station);
            }

        }

    }


    private Line saveLine(LineCode lineCode) {
        return lineRepository.save(new Line(lineCode.getCode(), lineCode.getName()));
    }

    private Station saveStation(PublicApiResponseDto.StationInfo stationInfo, Map<String, Station> stationMap) {
        Station station = stationMap.get(stationInfo.getStinNm());

        if (station == null) {
            station = new Station(stationInfo.getStinNm(), "", stationInfo.getMreaWideCd());
            stationRepository.save(station);
            stationMap.put(stationInfo.getStinNm(), station);
        }

        return station;
    }

    private LineStation saveLineStation(PublicApiResponseDto.StationInfo stationInfo, Line line, Station station) {
        LineStation lineStation = LineStation.builder()
                .line(line)
                .station(station)
                .orderInLine(stationInfo.getStinConsOrdr())
                .build();

        return lineStationRepository.save(lineStation);
    }

    private List<PublicApiResponseDto.StationInfo> fetchStationInfoList(String lineCode) {
        String url = "https://openapi.kric.go.kr/openapi/trainUseInfo/subwayRouteInfo";
        url = UriComponentsBuilder.fromUriString(url)
                .queryParam("serviceKey", "$2a$10$.P03IDI0V3/IewJhrarByeBimfbBi.doplIJyt9zJ/Ta5h0j9HQPi")
                .queryParam("format", "json")
                .queryParam("mreaWideCd", "01")
                .queryParam("lnCd", lineCode)
                .toUriString();

        PublicApiResponseDto response = restTemplate.getForObject(url, PublicApiResponseDto.class);

        if (response == null || response.getBody() == null) {
            return null;
        }

        return response.getBody();
    }

}
