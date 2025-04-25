package com.samsungnomads.wheretogo.domain.relationship.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 회원-필터 복합키 클래스
 * 🔑 회원-필터 매핑 테이블의 복합키를 나타내는 임베디드 타입
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MemberFilterId implements Serializable {
    
    @Column(name = "member_id")
    private Long memberId; // 👤 회원 ID (FK to member.id)
    
    @Column(name = "filter_id")
    private Long filterId; // 🔍 필터 ID (FK to filter.id)
} 