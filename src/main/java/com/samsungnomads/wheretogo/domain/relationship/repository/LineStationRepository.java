package com.samsungnomads.wheretogo.domain.relationship.repository;

import com.samsungnomads.wheretogo.domain.line.Line;
import com.samsungnomads.wheretogo.domain.relationship.entity.LineStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
    List<LineStation> findByLineOrderByOrderInLineAsc(Line line);
}
