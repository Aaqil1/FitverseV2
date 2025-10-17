package com.fitverse.foodvision.mapper;

import com.fitverse.foodvision.dto.FoodLogRequest;
import com.fitverse.foodvision.dto.FoodLogResponse;
import com.fitverse.foodvision.entity.FoodLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodLogMapper {

    FoodLogResponse toResponse(FoodLog log);

    FoodLog toEntity(FoodLogRequest request);
}
