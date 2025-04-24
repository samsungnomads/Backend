package com.samsungnomads.wheretogo.domain.filter.entity;

import com.samsungnomads.wheretogo.domain.member.entity.Member;
import com.samsungnomads.wheretogo.domain.relationship.entity.FilterStation;
import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 필터 엔티티
 * 🔍 역 필터링 정보를 저장하는 도메인 엔티티
 */
@Entity
@Table(name = "filter")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 🔑 필터 ID (AUTO INCREMENT)
    
    @Column(name = "name", length = 100)
    private String name; // 📝 필터 이름
    
    @Column(name = "is_shared")
    private Boolean isShared; // 🔄 공유 여부
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "uid")
    private Member owner; // 👤 필터를 소유한 회원
    
    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilterStation> stations = new ArrayList<>(); // 🚉 필터에 포함된 역 목록
    
    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberFilter> sharedMembers = new ArrayList<>(); // 👥 필터가 공유된 회원 목록
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 📅 생성 시간
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 🔄 수정 시간
    
    /**
     * 필터 생성자
     * 📝 필터의 기본 정보와 소유자 설정
     */
    @Builder
    public Filter(String name, Boolean isShared, Member owner) {
        this.name = name;
        this.isShared = isShared;
        this.owner = owner;
    }
    
    /**
     * 필터 정보 업데이트
     * 🔄 이름과 공유 여부만 변경 가능
     */
    public void update(String name, Boolean isShared) {
        if (name != null) this.name = name;
        if (isShared != null) this.isShared = isShared;
    }
}
