package com.samsungnomads.wheretogo.domain.filter.controller;

import com.samsungnomads.wheretogo.domain.filter.dto.*;
import com.samsungnomads.wheretogo.global.security.dto.UserDetailsImpl;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Filter", description = "필터 관련 API (로그인 후 사용 가능)")
public interface FilterControllerDocs {

    @Operation(
            summary = "내가 소유한 필터 조회",
            description = "로그인한 사용자가 소유한 필터 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내가 소유한 필터 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "내가 소유한 필터 응답 예시",
                                    summary = "내가 소유한 필터 목록을 조회한 응답 예시",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "사용자가 소유한 필터 목록을 성공적으로 조회했습니다.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "filterName": "나만의 추천 맛집 필터",
                                                        "creatorNickname": "JohnDoe",
                                                        "createdAt": "2023-05-01T10:00:00",
                                                        "updatedAt": "2023-05-15T08:30:00"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "filterName": "가족 여행지 필터",
                                                        "creatorNickname": "JaneDoe",
                                                        "createdAt": "2023-06-01T12:30:00",
                                                        "updatedAt": "2023-06-15T14:45:00"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<List<FilterPrivateOwnDto>>> getPrivateOwnFilters(
            @AuthenticationPrincipal UserDetailsImpl userDetails);


    @Operation(
            summary = "내가 생성한 필터 조회",
            description = "로그인한 사용자가 생성한 필터 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내가 생성한 필터 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "내가 생성한 필터 응답 예시",
                                    summary = "내가 생성한 필터 목록을 조회한 응답 예시",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "사용자가 생성한 필터 목록을 성공적으로 조회했습니다.",
                                                "data": [
                                                    {
                                                        "id": 3,
                                                        "filterName": "주말 여행지 필터",
                                                        "creatorNickname": "AdventureSeeker",
                                                        "createdAt": "2023-07-01T09:00:00",
                                                        "updatedAt": "2023-07-10T14:00:00"
                                                    },
                                                    {
                                                        "id": 4,
                                                        "filterName": "서울 카페 탐방 필터",
                                                        "creatorNickname": "CafeLover",
                                                        "createdAt": "2023-08-15T11:30:00",
                                                        "updatedAt": "2023-08-20T16:45:00"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<List<FilterPrivateCreationDto>>> getPrivateCreationFilters(
            @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(
            summary = "필터 상세 정보 조회",
            description = "특정 필터의 상세 정보를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "필터 상세 정보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "필터 상세 정보 응답 예시",
                                    summary = "필터의 상세 정보를 조회한 응답 예시",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "필터 상세 정보를 성공적으로 조회했습니다.",
                                                "data": {
                                                    "id": 1,
                                                    "name": "나만의 추천 맛집 필터",
                                                    "isShared": true,
                                                    "creatorNickname": "Foodie123",
                                                    "stationResponses": {
                                                        "1호선": [
                                                            {
                                                                "name": "서울역",
                                                                "orderInLine": 1,
                                                                "address": "서울특별시 중구 서울역로 1",
                                                                "area": "01"
                                                            },
                                                            {
                                                                "name": "시청역",
                                                                "orderInLine": 2,
                                                                "address": "서울특별시 중구 시청로 1",
                                                                "area": "01"
                                                            }
                                                        ],
                                                        "2호선": [
                                                            {
                                                                "name": "강남역",
                                                                "orderInLine": 3,
                                                                "address": "서울특별시 강남구 강남대로 102",
                                                                "area": "01"
                                                            }
                                                        ]
                                                    }
                                                }
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<FilterDetailDto>> getFilterDetail(
            @PathVariable("filterId") Long filterId);

    @Operation(
            summary = "공개 필터 목록 조회",
            description = "공개된 필터 목록을 조회할 수 있으며, 조건에 따라 필터링할 수 있습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "공개 필터 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "공개 필터 목록 조회 응답 예시",
                                    summary = "공개된 필터 목록을 조회한 응답 예시",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "필터 목록을 성공적으로 조회했습니다.",
                                                "data": [
                                                    {
                                                        "id": 1,
                                                        "name": "여행지 추천 필터",
                                                        "creatorNickname": "JohnDoe",
                                                        "likes": 120,
                                                        "createdAt": "2023-05-01T10:00:00",
                                                        "updatedAt": "2023-06-01T12:30:00"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "name": "가족 여행지 필터",
                                                        "creatorNickname": "JaneDoe",
                                                        "likes": 85,
                                                        "createdAt": "2023-04-20T09:30:00",
                                                        "updatedAt": "2023-05-05T14:00:00"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<Slice<FilterPublicDto>>> getPublicFilters(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) Integer lastLikes,
            @RequestParam(required = false) LocalDateTime lastUpdatedAt,
            @RequestParam(required = false) String keyword,
            @Parameter(
                    name = "pageable",
                    description = "페이지 번호 및 정렬 조건을 포함하는 Pageable 객체",
                    examples = {
                            @ExampleObject(name = "좋아요 내림차순", value = "sort=likes,desc"),
                            @ExampleObject(name = "좋아요 오름차순", value = "sort=likes,asc"),
                            @ExampleObject(name = "업데이트일 내림차순", value = "sort=updatedAt,desc"),
                            @ExampleObject(name = "업데이트일 오름차순", value = "sort=updatedAt,asc")
                    }
            )
            @PageableDefault(size = 5) Pageable pageable
    );

    @Operation(
            summary = "필터 삭제",
            description = "사용자가 소유한 필터를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "필터 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "필터 삭제 성공 응답 예시",
                                    summary = "필터 삭제 성공 시 응답 예시",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "필터를 성공적으로 삭제되었습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<Void>> deleteFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("filterId") Long filterId
    );

    @Operation(
            summary = "필터 다운로드",
            description = "공용 저장소에서 내 저장소로 필터를 다운로드합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "필터 다운로드 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "필터 다운로드 성공 응답 예시",
                                    summary = "필터 다운로드 성공 시 응답 예시",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "필터를 내 저장소로 성공적으로 다운로드했습니다",
                                                "data": {
                                                    "filterId": 1,
                                                    "filterName": "맛집 추천 필터",
                                                    "creatorNickname": "FoodExplorer",
                                                    "isShared": true
                                                }
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<FilterDownloadResponseDto>> downloadFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterDownloadRequestDto requestDto
    );
    
    @Operation(
            summary = "필터 업로드",
            description = "내 저장소의 필터를 공용 저장소로 업로드(공유)합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "필터 업로드 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "필터 업로드 성공 응답 예시",
                                    summary = "필터 업로드 성공 시 응답 예시",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "필터를 공용 저장소로 성공적으로 업로드했습니다",
                                                "data": {
                                                    "filterId": 2,
                                                    "filterName": "여행지 추천 필터",
                                                    "isShared": true
                                                }
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<FilterUploadResponseDto>> uploadFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterUploadRequestDto requestDto
    );
    
    @Operation(
            summary = "필터 생성",
            description = "새로운 필터를 생성하고 내 저장소에 저장합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "필터 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "필터 생성 성공 응답 예시",
                                    summary = "필터 생성 성공 시 응답 예시",
                                    value = """
                                            {
                                                "status": 201,
                                                "message": "필터를 성공적으로 생성하였습니다",
                                                "data": {
                                                    "filterId": 3,
                                                    "name": "새 여행지 필터",
                                                    "isShared": false,
                                                    "stationIds": [1, 2, 3, 4],
                                                    "createdAt": "2023-10-01T14:30:00"
                                                }
                                            }
                                            """
                            )
                    ))
    })
    public ResponseEntity<SuccessResponse<FilterSaveResponseDto>> saveFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterSaveRequestDto requestDto
    );
}
