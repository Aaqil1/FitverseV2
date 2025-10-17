package com.fitverse.analytics.service;

import com.fitverse.analytics.dto.AnalyticsResponse;
import com.fitverse.analytics.dto.CaptureAnalyticsRequest;
import com.fitverse.analytics.entity.AnalyticsSnapshot;
import com.fitverse.analytics.exception.AnalyticsNotFoundException;
import com.fitverse.analytics.mapper.AnalyticsMapper;
import com.fitverse.analytics.repository.AnalyticsSnapshotRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnalyticsService {

    private final AnalyticsSnapshotRepository repository;
    private final AnalyticsMapper mapper;

    public AnalyticsService(AnalyticsSnapshotRepository repository, AnalyticsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AnalyticsResponse capture(CaptureAnalyticsRequest request) {
        AnalyticsSnapshot snapshot = new AnalyticsSnapshot(request.getUserId(), request.getBmi(), request.getCaloriesDelta());
        AnalyticsSnapshot saved = repository.save(snapshot);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AnalyticsResponse> latestForUser(Long userId) {
        List<AnalyticsSnapshot> snapshots = repository.findTop10ByUserIdOrderByCapturedAtDesc(userId);
        if (snapshots.isEmpty()) {
            throw new AnalyticsNotFoundException(userId);
        }
        return snapshots.stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
