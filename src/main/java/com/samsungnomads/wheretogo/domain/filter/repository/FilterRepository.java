package com.samsungnomads.wheretogo.domain.filter.repository;

import com.samsungnomads.wheretogo.domain.filter.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterRepository extends JpaRepository<Filter, Long> {

    List<Filter> findByCreator_LoginId(String loginId);
}
