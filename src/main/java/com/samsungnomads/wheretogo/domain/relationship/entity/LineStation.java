package com.samsungnomads.wheretogo.domain.relationship.entity;

import com.samsungnomads.wheretogo.domain.line.Line;
import com.samsungnomads.wheretogo.domain.station.entity.Station;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "line_station")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "order_in_line")
    private Integer orderInLine;

    @Builder
    public LineStation(Line line, Station station, Integer orderInLine) {
        this.line = line;
        this.station = station;
        this.orderInLine = orderInLine;
    }

}
