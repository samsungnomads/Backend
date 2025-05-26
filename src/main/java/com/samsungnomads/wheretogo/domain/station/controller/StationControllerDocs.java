package com.samsungnomads.wheretogo.domain.station.controller;

import com.samsungnomads.wheretogo.domain.station.dto.RecommendationDto;
import com.samsungnomads.wheretogo.domain.station.dto.StationResponseDto;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Tag(name = "Station", description = "지하철역 관련 API (AccessToken이 필요하지 않습니다)")
public interface StationControllerDocs {

    @Operation(
            summary = "지하철역 전체 조회",
            description = "노선별로 묶인 모든 지하철역 리스트를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "역 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "역 목록 응답",
                                    summary = "지하철역을 노선별로 반환하는 예시",
                                    value = """
                                             {
                                               "status": 200,
                                               "message": "요청이 성공했습니다.",
                                               "data": {
                                                 "서해": [
                                                   {
                                                     "name": "일산",
                                                     "orderInLine": 1,
                                                     "address" : "",
                                                     "area" : "01"
                                                   },
                                                   {\s
                                                      "name": "풍산",
                                                      "orderInLine": 2,
                                                      "address": "",
                                                      "area": "01"
                                                    }
                                                 ],
                                                 "2호선": [
                                                   {
                                                     "stationName": "강남역",
                                                     "line": "2호선"
                                                   }
                                                 ]
                                               }
                                             }
                                            \s"""
                            )
                    )
            )
    })
    ResponseEntity<SuccessResponse<Map<String, List<StationResponseDto>>>> getAllStations();

    @Operation(
            summary = "역 기반 장소 추천",
            description = "특정 지하철역명을 기준으로 주변 추천 장소를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "추천 장소 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "추천 장소 응답",
                                    summary = "역 기반 추천 장소 리스트 예시",
                                    value = """
                                                    {
                                                        "status": 200,
                                                        "message": "요청이 성공했습니다.",
                                                        "data": {
                                                            "명소": [
                                                                {
                                                                    "name": "세실마루",
                                                                    "address": "서울 중구 세종대로19길 16",
                                                                    "phone": "",
                                                                    "url": "http://place.map.kakao.com/1841732402",
                                                                    "rating": 4.5
                                                                },
                                                                {
                                                                    "name": "안중근의사상",
                                                                    "address": "서울 중구 소월로 91",
                                                                    "phone": "",
                                                                    "url": "http://place.map.kakao.com/11308135",
                                                                    "rating": 4.5
                                                                }
                                                            ],
                                                            "맛집": [
                                                                {
                                                                    "name": "일미장어",
                                                                    "address": "서울 용산구 후암로57길 35-15",
                                                                    "phone": "02-777-4380",
                                                                    "url": "http://place.map.kakao.com/16039548",
                                                                    "rating": 4.5
                                                                },
                                                                {
                                                                    "name": "서령 본점",
                                                                    "address": "서울 중구 소월로 10",
                                                                    "phone": "0503-7150-8217",
                                                                    "url": "http://place.map.kakao.com/1644608542",
                                                                    "rating": 4.5
                                                                }
                                                            ],
                                                            "카페": [
                                                                {
                                                                    "name": "스타벅스 연세세브란스점",
                                                                    "address": "서울 중구 통일로 10",
                                                                    "phone": "",
                                                                    "url": "http://place.map.kakao.com/2129835684",
                                                                    "rating": 4.5
                                                                },
                                                                {
                                                                    "name": "드로우 에스프레소바",
                                                                    "address": "서울 중구 청파로 453",
                                                                    "phone": "0507-1477-8955",
                                                                    "url": "http://place.map.kakao.com/504184600",
                                                                    "rating": 4.5
                                                                }
                                                            ]
                                                        }
                                                    }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<SuccessResponse<Map<String, List<RecommendationDto>>>> getRecommendation(
            @RequestParam String stationName
    );

}