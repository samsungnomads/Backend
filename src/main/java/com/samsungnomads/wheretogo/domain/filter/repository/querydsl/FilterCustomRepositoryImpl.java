package com.samsungnomads.wheretogo.domain.filter.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.samsungnomads.wheretogo.domain.filter.dto.FilterConditionDto;
import com.samsungnomads.wheretogo.domain.filter.dto.FilterPublicDto;
import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.global.common.SliceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.samsungnomads.wheretogo.domain.filter.entity.QFilter.filter;


@Slf4j
@Repository
@RequiredArgsConstructor
public class FilterCustomRepositoryImpl implements FilterCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<FilterPublicDto> findByConditionWithPageable(Long cursorId, FilterConditionDto condition, Pageable pageable) {

        BooleanExpression whereCondition = createWhereCondition(cursorId, condition, pageable);

        List<Filter> filters = jpaQueryFactory.selectFrom(filter)
                .where(whereCondition)
                .limit(SliceUtil.getLimit(pageable))
                .orderBy(SliceUtil.getOrderSpecifier(pageable))
                .fetch();

        return SliceUtil.toSlice(pageable, filters, FilterPublicDto::from);
    }

    private BooleanExpression createWhereCondition(Long cursorId, FilterConditionDto condition, Pageable pageable) {
        BooleanExpression cursorCondition = buildCursorCondition(cursorId, condition, pageable);
        BooleanExpression keywordCondition = buildKeywordCondition(condition.getKeyword());

        return combineConditions(cursorCondition, keywordCondition);
    }


    private BooleanExpression buildCursorCondition(Long cursorId, FilterConditionDto condition, Pageable pageable) {
        if (cursorId == null) return null;

        String property = getSortOrder(pageable).getProperty();
        Sort.Direction direction = getSortOrder(pageable).getDirection();

        return switch (property) {
            case "likes" -> buildLikesCondition(condition.getLastLikes(), cursorId, direction);
            case "updatedAt" -> buildUpdatedAtCondition(condition.getLastUpdatedAt(), cursorId, direction);
            default -> buildIdCondition(cursorId, direction);
        };
    }

    private BooleanExpression buildKeywordCondition(String keywords) {
        if (keywords == null || keywords.trim().isEmpty()) {
            return null;
        }

        String[] keywordArray = keywords.trim().split("\\s+");

        BooleanExpression result = null;
        for (String keyword : keywordArray) {
            BooleanExpression condition = filter.name.containsIgnoreCase(keyword);
            result = (result == null)
                    ? condition : result.and(condition);
        }

        return result;
    }

    private BooleanExpression combineConditions(BooleanExpression a, BooleanExpression b) {
        if (a != null && b != null) return a.and(b);
        if (a != null) return a;
        return b;
    }


    private BooleanExpression buildLikesCondition(Integer lastLikes, Long cursorId, Sort.Direction direction) {
        log.info("buildLikesCondition: lastLikes={}, direction={}", lastLikes, direction);
        if (lastLikes == null || cursorId == null) return null;

        return direction.isAscending() ?
                filter.likes.gt(lastLikes)
                        .or(filter.likes.eq(lastLikes).and(filter.id.gt(cursorId))) :
                filter.likes.lt(lastLikes)
                        .or(filter.likes.eq(lastLikes).and(filter.id.lt(cursorId)));
    }

    private BooleanExpression buildUpdatedAtCondition(LocalDateTime lastUpdatedAt, Long cursorId, Sort.Direction direction) {
        log.info("buildUpdatedAtCondition: lastUpdatedAt={}, direction={}", lastUpdatedAt, direction);
        if (lastUpdatedAt == null || cursorId == null) return null;

        return direction.isAscending() ?
                filter.updatedAt.gt(lastUpdatedAt)
                        .or(filter.updatedAt.eq(lastUpdatedAt).and(filter.id.gt(cursorId))) :
                filter.updatedAt.lt(lastUpdatedAt)
                        .or(filter.updatedAt.eq(lastUpdatedAt).and(filter.id.lt(cursorId)));
    }

    private BooleanExpression buildIdCondition(Long cursorId, Sort.Direction direction) {
        log.info("buildIdCondition: cursorId={}, direction={}", cursorId, direction);
        if (cursorId == null) return null;

        return direction.isAscending() ?
                filter.id.gt(cursorId) :
                filter.id.lt(cursorId);
    }

    private Sort.Order getSortOrder(Pageable pageable) {
        return pageable.getSort().stream().findFirst()
                .orElse(new Sort.Order(Sort.Direction.DESC, "id")); // 기본 정렬
    }


}
