package com.samsungnomads.wheretogo.domain.member.entity;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import com.samsungnomads.wheretogo.domain.relationship.entity.MemberFilter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 엔티티
 * 👤 사용자 정보를 저장하는 기본 도메인 엔티티
 */
@Builder
@Entity
@Table(name = "member", uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_login_id", columnNames = {"login_id"}),
        @UniqueConstraint(name = "uk_member_email", columnNames = {"email"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 🔑 회원 기본 키 (AUTO INCREMENT)

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId; // 🆔 회원 로그인 아이디

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 📧 회원 이메일

    @Column(name = "password", nullable = false)
    private String password; // 🔒 비밀번호

    @Column(name = "nickname", length = 100)
    private String nickname; // 👨‍💼 회원 닉네임

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Filter> createdFilters = new ArrayList<>(); // 🔍 제작한 필터 목록

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberFilter> sharedFilters = new ArrayList<>(); // 🔄 공유받은 필터 목록

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 📅 생성 시간

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 🔄 수정 시간

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "roles")
    private List<String> roles = new ArrayList<>();

    /**
     * 회원 생성자
     * 📝 회원 정보 초기화
     */
    @Builder
    public Member(String loginId, String email, String password, String nickname) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }


    /**
     * 회원 정보 업데이트
     * 🔄 비밀번호와 닉네임만 변경 가능
     */
    public void update(String password, String nickname) {
        if (password != null) this.password = password;
        if (nickname != null) this.nickname = nickname;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
