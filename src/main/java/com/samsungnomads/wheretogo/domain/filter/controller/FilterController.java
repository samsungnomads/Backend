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
    public ResponseEntity<SuccessResponse<List<FilterPrivateOwnDto>>> getPrivateOwnFilters(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FilterPrivateOwnDto> filterPrivateOwnDtos = filterService.getPrivateOwnFiltersByLoginId(userDetails.getUsername());
        return SuccessResponse.of(SuccessCode.FILTER_PRIVATE_OWN, filterPrivateOwnDtos);
    }

    @GetMapping("/private/creations")
    public ResponseEntity<SuccessResponse<List<FilterPrivateCreationDto>>> getPrivateCreationFilters(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FilterPrivateCreationDto> filterPrivateCreationDtos = filterService.getPrivateCreationFiltersByLoginId(userDetails.getUsername());
        return SuccessResponse.of(SuccessCode.FILTER_PRIVATE_CREATION, filterPrivateCreationDtos);
    }

    @GetMapping("/{filterId}")
    public ResponseEntity<SuccessResponse<FilterDetailDto>> getFilterDetail(@PathVariable("filterId") Long filterId) {
        FilterDetailDto filterDetailDto = filterService.getFilterDetail(filterId);
        return SuccessResponse.of(SuccessCode.FILTER_DETAIL, filterDetailDto);
    }

    @GetMapping("/public")
    public ResponseEntity<SuccessResponse<Slice<FilterPublicDto>>> getPublicFilters(
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) Integer lastLikes,
            @RequestParam(required = false) LocalDateTime lastUpdatedAt,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        Slice<FilterPublicDto> filterPublicDtos = filterService.getPublicFilters(cursorId, new FilterConditionDto(lastLikes, lastUpdatedAt), pageable);
        return SuccessResponse.of(SuccessCode.OK, filterPublicDtos);

    }

}
