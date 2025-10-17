package com.fitverse.fitness.repository;

import com.fitverse.fitness.entity.WorkoutLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {

    List<WorkoutLog> findByUserId(Long userId);
}
