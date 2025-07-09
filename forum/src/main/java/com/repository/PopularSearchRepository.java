package com.repository;

import com.entity.PopularSearchVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularSearchRepository extends JpaRepository<PopularSearchVO, String> {
}