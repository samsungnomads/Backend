package com.samsungnomads.wheretogo.domain.filter.controller;

import com.samsungnomads.wheretogo.domain.filter.dto.*;
import com.samsungnomads.wheretogo.domain.filter.service.FilterService;
import com.samsungnomads.wheretogo.global.security.dto.UserDetailsImpl;
import com.samsungnomads.wheretogo.global.success.SuccessCode;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/filter")
@RequiredArgsConstructor
public class FilterController implements FilterControllerDocs {
    private final FilterService filterService;

    @GetMapping("/private/owns")
    @Override
    public ResponseEntity<SuccessResponse<List<FilterPrivateOwnDto>>> getPrivateOwnFilters(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FilterPrivateOwnDto> filterPrivateOwnDtos = filterService.getPrivateOwnFiltersByLoginId(userDetails.getUsername());
        return SuccessResponse.of(SuccessCode.FILTER_PRIVATE_OWN, filterPrivateOwnDtos);
    }

    @GetMapping("/private/creations")
    @Override
    public ResponseEntity<SuccessResponse<List<FilterPrivateCreationDto>>> getPrivateCreationFilters(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FilterPrivateCreationDto> filterPrivateCreationDtos = filterService.getPrivateCreationFiltersByLoginId(userDetails.getUsername());
        return SuccessResponse.of(SuccessCode.FILTER_PRIVATE_CREATION, filterPrivateCreationDtos);
    }

    @GetMapping("/{filterId}")
    @Override
    public ResponseEntity<SuccessResponse<FilterDetailDto>> getFilterDetail(@PathVariable("filterId") Long filterId) {
        FilterDetailDto filterDetailDto = filterService.getFilterDetail(filterId);
        return SuccessResponse.of(SuccessCode.FILTER_DETAIL, filterDetailDto);
    }

    @GetMapping("/public")
    @Override
    public ResponseEntity<SuccessResponse<Slice<FilterPublicDto>>> getPublicFilters(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) Integer lastLikes,
            @RequestParam(required = false) LocalDateTime lastUpdatedAt,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        FilterConditionDto condition = new FilterConditionDto(lastLikes, lastUpdatedAt, keyword);
        Slice<FilterPublicDto> filterPublicDtos = filterService.getPublicFilters(cursorId, condition, pageable);
        return SuccessResponse.of(SuccessCode.OK, filterPublicDtos);

    }

    @DeleteMapping("/private/{filterId}")
    @Override
    public ResponseEntity<SuccessResponse<Void>> deleteFilter(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("filterId") Long filterId) {
        filterService.deleteFilter(userDetails.getUsername(), filterId);
        return SuccessResponse.of(SuccessCode.FILTER_DELETE, null);
    }
    
    /**
     * 필터 다운로드
     * 📥 공용 저장소에서 내 저장소로 필터 다운로드
     */
    @PostMapping("/download")
    @Override
    public ResponseEntity<SuccessResponse<FilterDownloadResponseDto>> downloadFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterDownloadRequestDto requestDto
    ) {
        FilterDownloadResponseDto responseDto = filterService.downloadFilter(
                userDetails.getUsername(),
                requestDto.getFilterId()
        );
        
        return SuccessResponse.of(SuccessCode.FILTER_DOWNLOAD_SUCCESS, responseDto);
    }
    
    /**
     * 필터 업로드
     * 📤 내 저장소에서 공용 저장소로 필터 업로드
     */
    @PostMapping("/upload")
    @Override
    public ResponseEntity<SuccessResponse<FilterUploadResponseDto>> uploadFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterUploadRequestDto requestDto
    ) {
        FilterUploadResponseDto responseDto = filterService.uploadFilter(
                userDetails.getUsername(),
                requestDto.getFilterId(),
                requestDto.getIsShared()
        );
        
        return SuccessResponse.of(SuccessCode.FILTER_UPLOAD_SUCCESS, responseDto);
    }
    
    /**
     * 필터 생성
     * 🆕 새로운 필터를 생성하고 내 저장소에 저장
     */
    @PostMapping("/save")
    @Override
    public ResponseEntity<SuccessResponse<FilterSaveResponseDto>> saveFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterSaveRequestDto requestDto
    ) {
        FilterSaveResponseDto responseDto = filterService.createFilter(
                userDetails.getUsername(),
                requestDto.getName(),
                requestDto.getIsShared(),
                requestDto.getStationIds()
        );
        
        return SuccessResponse.of(SuccessCode.FILTER_SAVE_SUCCESS, responseDto);
    }

    /**
     * 필터 좋아요 기능
     * 필터에 좋아요를 추가합니다.
     */
    @PostMapping("/like")
    @Override
    public ResponseEntity<SuccessResponse<FilterLikeResponseDto>> toggleFilterLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterLikeRequest request) {
        FilterLikeResponseDto responseDto = filterService.toggleFilterLike(userDetails.getUsername(), request);
        return SuccessResponse.of(SuccessCode.OK, responseDto);
    }

    /**
     * 필터 수정
     * 내가 제작한/공유받은 필터를 수정합니다.
     */
    @PostMapping("/edit")
    @Override
    public ResponseEntity<SuccessResponse<FilterEditResponseDto>> editFilter(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FilterEditRequest request) {
        FilterEditResponseDto responseDto = filterService.editFilter(
                userDetails.getUsername(),
                request
        );
        
        return SuccessResponse.of(SuccessCode.FILTER_EDIT_SUCCESS, responseDto);
    }
}
