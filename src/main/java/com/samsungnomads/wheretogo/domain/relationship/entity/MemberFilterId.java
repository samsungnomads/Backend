package com.samsungnomads.wheretogo.domain.relationship.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * MemberFilter 복합키 클래스
 * 🔑 회원-필터 매핑 테이블의 복합키를 나타내는 클래스
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MemberFilterId implements Serializable {
    
    private Long memberUid; // 👤 회원 ID (FK to member.uid)
    private Long filterId; // 🔍 필터 ID (FK to filter.id)
} 