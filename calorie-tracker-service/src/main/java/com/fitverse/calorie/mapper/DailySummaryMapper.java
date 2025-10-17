package com.fitverse.calorie.mapper;

import com.fitverse.calorie.dto.DailySummaryResponse;
import com.fitverse.calorie.entity.DailySummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailySummaryMapper {

    DailySummaryResponse toResponse(DailySummary summary);
}
