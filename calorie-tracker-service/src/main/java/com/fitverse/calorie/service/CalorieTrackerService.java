package com.fitverse.calorie.service;

import com.fitverse.calorie.dto.DailySummaryResponse;
import com.fitverse.calorie.dto.UpsertDailySummaryRequest;
import com.fitverse.calorie.entity.DailySummary;
import com.fitverse.calorie.exception.SummaryNotFoundException;
import com.fitverse.calorie.mapper.DailySummaryMapper;
import com.fitverse.calorie.repository.DailySummaryRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CalorieTrackerService {

    private final DailySummaryRepository repository;
    private final DailySummaryMapper mapper;

    public CalorieTrackerService(DailySummaryRepository repository, DailySummaryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public DailySummaryResponse getDailySummary(Long userId, LocalDate date) {
        DailySummary summary = repository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> new SummaryNotFoundException(userId));
        return mapper.toResponse(summary);
    }

    @Transactional(readOnly = true)
    public List<DailySummaryResponse> getWeeklySummary(Long userId, LocalDate start, LocalDate end) {
        return repository.findByUserIdAndDateBetween(userId, start, end).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public DailySummaryResponse upsertSummary(UpsertDailySummaryRequest request) {
        DailySummary summary = repository.findByUserIdAndDate(request.getUserId(), request.getDate())
                .orElse(new DailySummary(request.getUserId(), request.getDate(), request.getTotalCalories()));
        summary.setTotalCalories(request.getTotalCalories());
        DailySummary saved = repository.save(summary);
        return mapper.toResponse(saved);
    }
}
