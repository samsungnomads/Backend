package com.samsungnomads.wheretogo.domain.station.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 역 엔티티
 * 🚉 지하철/철도 역 정보를 저장하는 도메인 엔티티
 */
@Entity
@Table(name = "station")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 🔑 역 ID (AUTO INCREMENT)
    
    @Column(name = "name", nullable = false, length = 100)
    private String name; // 📝 역 이름
    
    @Column(name = "line", length = 50)
    private String line; // 🚇 노선명
    
    @Column(name = "adress")
    private String adress; // 📍 주소 정보
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 📅 생성 시간
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 🔄 수정 시간
    
    /**
     * 역 생성자
     * 📝 역 정보 초기화
     */
    @Builder
    public Station(String name, String line, String adress) {
        this.name = name;
        this.line = line;
        this.adress = adress;
    }
    
    /**
     * 역 정보 업데이트
     * 🔄 역 정보 변경
     */
    public void update(String name, String line, String adress) {
        if (name != null) this.name = name;
        if (line != null) this.line = line;
        if (adress != null) this.adress = adress;
    }
}
