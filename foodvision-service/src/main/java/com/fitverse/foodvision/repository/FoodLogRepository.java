package com.fitverse.foodvision.repository;

import com.fitverse.foodvision.entity.FoodLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodLogRepository extends JpaRepository<FoodLog, Long> {

    List<FoodLog> findByUserId(Long userId);
}
