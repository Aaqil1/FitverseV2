package com.fitverse.calorie.repository;

import com.fitverse.calorie.entity.DailySummary;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {

    Optional<DailySummary> findByUserIdAndDate(Long userId, LocalDate date);

    List<DailySummary> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
