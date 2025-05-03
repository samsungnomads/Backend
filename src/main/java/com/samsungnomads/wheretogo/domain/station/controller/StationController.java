package com.samsungnomads.wheretogo.domain.station.controller;

import com.samsungnomads.wheretogo.domain.station.dto.RecommendationDto;
import com.samsungnomads.wheretogo.domain.station.dto.StationResponseDto;
import com.samsungnomads.wheretogo.domain.station.service.StationService;
import com.samsungnomads.wheretogo.global.success.SuccessCode;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/station")
@RequiredArgsConstructor
public class StationController implements StationControllerDocs {

    private final StationService stationService;

    @GetMapping("/stations")
    public ResponseEntity<SuccessResponse<Map<String, List<StationResponseDto>>>> getAllStations() {
        Map<String, List<StationResponseDto>> stationsGroupedByLine = stationService.getAllStationsGroupedByLine();
        return SuccessResponse.of(SuccessCode.OK, stationsGroupedByLine);
    }

    @GetMapping("/recommendation")
    public ResponseEntity<SuccessResponse<Map<String, List<RecommendationDto>>>> getRecommendation(@RequestParam String stationName) {

        Map<String, List<RecommendationDto>> recommendations = stationService.getRecommendations(stationName);
        return SuccessResponse.of(SuccessCode.OK, recommendations);
    }

}
