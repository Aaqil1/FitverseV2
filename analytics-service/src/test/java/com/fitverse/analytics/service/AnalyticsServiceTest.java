package com.fitverse.analytics.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fitverse.analytics.dto.CaptureAnalyticsRequest;
import com.fitverse.analytics.entity.AnalyticsSnapshot;
import com.fitverse.analytics.exception.AnalyticsNotFoundException;
import com.fitverse.analytics.mapper.AnalyticsMapper;
import com.fitverse.analytics.repository.AnalyticsSnapshotRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private AnalyticsSnapshotRepository repository;

    private final AnalyticsMapper mapper = Mappers.getMapper(AnalyticsMapper.class);

    private AnalyticsService service;

    @BeforeEach
    void setUp() {
        service = new AnalyticsService(repository, mapper);
    }

    @Test
    void captureSavesSnapshot() {
        CaptureAnalyticsRequest request = new CaptureAnalyticsRequest();
        request.setUserId(1L);
        request.setBmi(24.0);
        request.setCaloriesDelta(200);

        when(repository.save(any(AnalyticsSnapshot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(service.capture(request).getBmi()).isEqualTo(24.0);
    }

    @Test
    void latestForUserThrowsWhenEmpty() {
        when(repository.findTop10ByUserIdOrderByCapturedAtDesc(1L)).thenReturn(Collections.emptyList());
        assertThrows(AnalyticsNotFoundException.class, () -> service.latestForUser(1L));
    }

    @Test
    void latestForUserReturnsSnapshots() {
        when(repository.findTop10ByUserIdOrderByCapturedAtDesc(1L)).thenReturn(List.of(new AnalyticsSnapshot(1L, 23.5, 150)));
        assertThat(service.latestForUser(1L)).hasSize(1);
    }
}
