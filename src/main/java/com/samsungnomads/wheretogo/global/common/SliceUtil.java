package com.samsungnomads.wheretogo.global.common;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.samsungnomads.wheretogo.domain.filter.entity.QFilter.filter;

@Slf4j
public class SliceUtil {
    private static final String ORDER_BY_UPDATED_AT = "updatedAt";
    private static final String ORDER_BY_LIKES = "likes";

    public static int getLimit(Pageable pageable) {
        return pageable.getPageSize() + 1;
    }

    public static OrderSpecifier<?> getOrderSpecifier(Pageable pageable) {
        for (Sort.Order order : pageable.getSort()) {

            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            return switch (order.getProperty()) {
                case ORDER_BY_UPDATED_AT -> new OrderSpecifier<>(direction, filter.updatedAt);
                case ORDER_BY_LIKES -> new OrderSpecifier<>(direction, filter.likes);
                default -> new OrderSpecifier<>(direction, filter.id);
            };
        }
        return new OrderSpecifier<>(Order.DESC, filter.id);
    }

    public static <T, R> SliceImpl<R> toSlice(Pageable pageable, List<T> lst, Function<T, R> mapper) {

        // 다음 페이지가 존재하는지 확인: 요청한 pageSize보다 1개 더 가져왔다면 true
        boolean hasNext = lst.size() > pageable.getPageSize();

        // hasNext가 true인 경우, 실제 반환할 데이터에서는 초과된 마지막 요소 1개 제거
        if (hasNext) {
            lst.remove(pageable.getPageSize());
        }

        // Entity 리스트를 DTO 등으로 변환하고 SliceImpl로 감싸서 리턴
        return new SliceImpl<>(
                lst.stream()
                        .map(mapper) // 예: Diary → DiaryBriefResponse
                        .collect(Collectors.toList()), // 변환된 리스트
                pageable, // 요청된 페이징 정보 그대로 전달
                hasNext   // 다음 페이지 존재 여부 포함
        );
    }
}
