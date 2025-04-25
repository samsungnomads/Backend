package com.samsungnomads.wheretogo.domain.relationship.entity;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원-필터 매핑 엔티티
 * 🔄 회원과 필터 간의 다대다 관계를 표현하는 연결 테이블 엔티티
 */
@Entity
@Table(name = "member_filter")
@IdClass(MemberFilterId.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId; // 👤 회원 ID 값 (중복 필드)
    
    @Id
    @Column(name = "filter_id", insertable = false, updatable = false)
    private Long filterId; // 🔍 필터 ID 값 (중복 필드)
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member; // 👤 회원 참조
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id", referencedColumnName = "id")
    private Filter filter; // 🔗 필터 참조
    
    /**
     * 회원-필터 관계 생성
     * 📝 회원과 필터를 연결
     */
    @Builder
    public MemberFilter(Member member, Filter filter) {
        this.member = member;
        this.filter = filter;
        this.memberId = member.getId();
        this.filterId = filter.getId();
    }
} 