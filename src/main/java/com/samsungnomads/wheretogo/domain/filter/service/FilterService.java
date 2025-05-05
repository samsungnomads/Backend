package com.samsungnomads.wheretogo.domain.filter.service;

import com.samsungnomads.wheretogo.domain.filter.dto.FilterPrivateCreationDto;
import com.samsungnomads.wheretogo.domain.filter.dto.FilterPrivateOwnDto;
import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.filter.repository.FilterRepository;
import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.member.repository.MemberRepository;
import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilter;
import com.samsungnomads.wheretogo.domain.relationship.repository.MemberFilterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;
    private final MemberFilterRepository memberFilterRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<FilterPrivateOwnDto> getPrivateOwnFiltersByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid loginId: " + loginId));

        List<MemberFilter> memberFilters = memberFilterRepository.findByMemberId(member.getId());

        return memberFilters.stream()
                .map(memberFilter -> FilterPrivateOwnDto.from(memberFilter.getFilter()))
                .toList();
    }

    @Transactional
    public List<FilterPrivateCreationDto> getPrivateCreationFiltersByLoginId(String loginId) {
        List<Filter> filters = filterRepository.findByCreator_LoginId(loginId);

        return filters.stream()
                .map(FilterPrivateCreationDto::from)
                .toList();
    }
}
