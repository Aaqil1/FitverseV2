package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.WorkoutRecommendation;
import com.fitverse.shared.ai.dto.WorkoutRequestContext;

public interface RecommendationEngine {

    WorkoutRecommendation recommendPlan(WorkoutRequestContext context);
}
