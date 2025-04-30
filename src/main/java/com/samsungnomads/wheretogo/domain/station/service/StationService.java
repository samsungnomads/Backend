package com.samsungnomads.wheretogo.domain.station.service;

import com.samsungnomads.wheretogo.domain.line.Line;
import com.samsungnomads.wheretogo.domain.line.LineRepository;
import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import com.samsungnomads.wheretogo.domain.relationship.repository.LineStationRepository;
import com.samsungnomads.wheretogo.domain.station.dto.RecommendationDto;
import com.samsungnomads.wheretogo.domain.station.dto.StationResponseDto;
import com.samsungnomads.wheretogo.external.KakaoApiResponseDto;
import com.samsungnomads.wheretogo.external.KakaoApiService;
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
    private final KakaoApiService kakaoApiService;

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

    public Map<String, List<RecommendationDto>> getRecommendations(String stationName) {
        List<KakaoApiResponseDto.Document> recommendedRestaurants = kakaoApiService.fetchPlaceList(stationName, "맛집");
        List<KakaoApiResponseDto.Document> recommendedCafes = kakaoApiService.fetchPlaceList(stationName, "카페");
        List<KakaoApiResponseDto.Document> recommendedPlaces = kakaoApiService.fetchPlaceList(stationName, "명소");


        Map<String, List<RecommendationDto>> recommendationsByCategory = new HashMap<>();
        recommendationsByCategory.put("맛집", convertToDtoList(recommendedRestaurants));
        recommendationsByCategory.put("카페", convertToDtoList(recommendedCafes));
        recommendationsByCategory.put("명소", convertToDtoList(recommendedPlaces));

        return recommendationsByCategory;

    }

    private List<RecommendationDto> convertToDtoList(List<KakaoApiResponseDto.Document> documents) {
        List<RecommendationDto> recommendationDtos = new ArrayList<>();
        for (KakaoApiResponseDto.Document document : documents) {
            String name = document.getName();
            String address = document.getAddress();
            String phone = document.getPhone();
            String url = document.getUrl();
            Double rating = 4.5; // 우선은 하드코딩

            RecommendationDto recommendationDto = new RecommendationDto(name, address, phone, url, rating);
            recommendationDtos.add(recommendationDto);
        }

        return recommendationDtos;
    }

}
