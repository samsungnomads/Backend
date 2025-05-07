package com.samsungnomads.wheretogo.domain.line;

import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import com.samsungnomads.wheretogo.domain.station.entity.enums.LineCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "line")
@Getter
@NoArgsConstructor
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LineStation> lineStations;

    @Builder
    public Line(LineCode lineCode) {
        this.code = lineCode.getCode();
        this.name = lineCode.getName();
    }
}
