package com.fitverse.analytics.mapper;

import com.fitverse.analytics.dto.AnalyticsResponse;
import com.fitverse.analytics.entity.AnalyticsSnapshot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnalyticsMapper {

    AnalyticsResponse toResponse(AnalyticsSnapshot snapshot);
}
