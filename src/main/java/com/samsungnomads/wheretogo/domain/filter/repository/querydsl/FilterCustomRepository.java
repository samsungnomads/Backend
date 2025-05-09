package com.samsungnomads.wheretogo.domain.filter.repository.querydsl;

import com.samsungnomads.wheretogo.domain.filter.dto.FilterConditionDto;
import com.samsungnomads.wheretogo.domain.filter.dto.FilterPublicDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FilterCustomRepository {

    Slice<FilterPublicDto> findByConditionWithPageable(Long cursorId, FilterConditionDto condition, Pageable pageable);
}
