package com.fitverse.fitness.repository;

import com.fitverse.fitness.entity.WorkoutPlan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    Optional<WorkoutPlan> findTopByUserIdOrderByStartsOnDesc(Long userId);
}
