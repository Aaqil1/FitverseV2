package com.fitverse.fitness.mapper;

import com.fitverse.fitness.dto.WorkoutPlanResponse;
import com.fitverse.fitness.entity.WorkoutPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkoutPlanMapper {

    WorkoutPlanResponse toResponse(WorkoutPlan plan);
}
