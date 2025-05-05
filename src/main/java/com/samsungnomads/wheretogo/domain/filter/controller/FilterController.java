package com.samsungnomads.wheretogo.domain.filter.controller;

import com.samsungnomads.wheretogo.domain.filter.dto.FilterPrivateCreationDto;
import com.samsungnomads.wheretogo.domain.filter.dto.FilterPrivateOwnDto;
import com.samsungnomads.wheretogo.domain.filter.service.FilterService;
import com.samsungnomads.wheretogo.global.security.dto.UserDetailsImpl;
import com.samsungnomads.wheretogo.global.success.SuccessCode;
import com.samsungnomads.wheretogo.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return SuccessResponse.of(SuccessCode.OK, filterPrivateOwnDtos);
    }

    @GetMapping("/private/creations")
    public ResponseEntity<SuccessResponse<List<FilterPrivateCreationDto>>> getPrivateCreationFilters(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FilterPrivateCreationDto> filterPrivateCreationDtos = filterService.getPrivateCreationFiltersByLoginId(userDetails.getUsername());
        return SuccessResponse.of(SuccessCode.OK, filterPrivateCreationDtos);
    }

}
