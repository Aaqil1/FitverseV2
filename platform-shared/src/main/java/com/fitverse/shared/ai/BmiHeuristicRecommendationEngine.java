package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.WorkoutRecommendation;
import com.fitverse.shared.ai.dto.WorkoutRequestContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BmiHeuristicRecommendationEngine implements RecommendationEngine {

    @Override
    public WorkoutRecommendation recommendPlan(WorkoutRequestContext context) {
        double bmi = context.getBmi();
        String goal = context.getGoal().toLowerCase();
        if (bmi < 18.5 || goal.contains("gain")) {
            return new WorkoutRecommendation(List.of("Strength Training", "Protein Focus"), "Focus on hypertrophy and calorie surplus.");
        } else if (bmi > 30 || goal.contains("lose")) {
            return new WorkoutRecommendation(List.of("HIIT", "Cardio Intervals"), "Prioritize calorie deficit with interval training.");
        }
        return new WorkoutRecommendation(List.of("Functional Training", "Mobility"), "Maintain balanced routine with moderate intensity.");
    }
}
