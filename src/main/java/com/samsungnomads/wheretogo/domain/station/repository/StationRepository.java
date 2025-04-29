package com.samsungnomads.wheretogo.domain.station.repository;

import com.samsungnomads.wheretogo.domain.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
