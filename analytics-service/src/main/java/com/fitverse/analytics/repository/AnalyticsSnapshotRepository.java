package com.fitverse.analytics.repository;

import com.fitverse.analytics.entity.AnalyticsSnapshot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsSnapshotRepository extends JpaRepository<AnalyticsSnapshot, Long> {

    List<AnalyticsSnapshot> findTop10ByUserIdOrderByCapturedAtDesc(Long userId);
}
