package com.fitverse.calorie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fitverse.calorie.dto.UpsertDailySummaryRequest;
import com.fitverse.calorie.entity.DailySummary;
import com.fitverse.calorie.exception.SummaryNotFoundException;
import com.fitverse.calorie.mapper.DailySummaryMapper;
import com.fitverse.calorie.repository.DailySummaryRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalorieTrackerServiceTest {

    @Mock
    private DailySummaryRepository repository;

    private final DailySummaryMapper mapper = Mappers.getMapper(DailySummaryMapper.class);

    private CalorieTrackerService service;

    @BeforeEach
    void setUp() {
        service = new CalorieTrackerService(repository, mapper);
    }

    @Test
    void getDailySummaryThrowsWhenMissing() {
        when(repository.findByUserIdAndDate(1L, LocalDate.now())).thenReturn(Optional.empty());
        assertThrows(SummaryNotFoundException.class, () -> service.getDailySummary(1L, LocalDate.now()));
    }

    @Test
    void getWeeklySummaryReturnsEntries() {
        when(repository.findByUserIdAndDateBetween(1L, LocalDate.now(), LocalDate.now()))
                .thenReturn(Collections.singletonList(new DailySummary(1L, LocalDate.now(), 2000)));
        assertThat(service.getWeeklySummary(1L, LocalDate.now(), LocalDate.now())).hasSize(1);
    }

    @Test
    void upsertSummarySavesEntity() {
        UpsertDailySummaryRequest request = new UpsertDailySummaryRequest();
        request.setUserId(1L);
        request.setDate(LocalDate.now());
        request.setTotalCalories(1800);

        when(repository.findByUserIdAndDate(1L, request.getDate())).thenReturn(Optional.empty());
        when(repository.save(any(DailySummary.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(service.upsertSummary(request).getTotalCalories()).isEqualTo(1800);
    }
}
