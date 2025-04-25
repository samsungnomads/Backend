package com.samsungnomads.wheretogo.domain.relationship.entity;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import jakarta.persistence.*;
import lombok.*;

/**
 * 회원-필터 매핑 엔티티
 * 🔄 회원과 필터 간의 다대다 관계를 표현하는 연결 테이블 엔티티
 */
@Entity
@Table(name = "member_filter")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFilter {

    @EmbeddedId
    private MemberFilterId id; // 🔑 복합 기본 키
    
    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 👤 관련 회원 정보

    @MapsId("filterId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id")
    private Filter filter; // 🔍 관련 필터 정보

    /**
     * 회원-필터 관계 생성
     * 📝 회원과 필터를 연결
     */
    @Builder
    public MemberFilter(Member member, Filter filter) {
        this.id = new MemberFilterId(member.getId(), filter.getId());
        this.member = member;
        this.filter = filter;
    }
} 