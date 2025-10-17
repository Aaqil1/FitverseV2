package com.fitverse.foodvision.controller;

import com.fitverse.foodvision.dto.AnalyzeFoodRequest;
import com.fitverse.foodvision.dto.FoodAnalysisResponse;
import com.fitverse.foodvision.dto.FoodLogRequest;
import com.fitverse.foodvision.dto.FoodLogResponse;
import com.fitverse.foodvision.service.FoodVisionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
public class FoodVisionController {

    private final FoodVisionService service;

    public FoodVisionController(FoodVisionService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public FoodAnalysisResponse analyze(@Valid @RequestBody AnalyzeFoodRequest request) {
        return service.analyzeFood(request);
    }

    @PostMapping("/logs")
    @ResponseStatus(HttpStatus.CREATED)
    public FoodLogResponse logFood(@Valid @RequestBody FoodLogRequest request) {
        return service.logFood(request);
    }

    @GetMapping("/logs")
    public List<FoodLogResponse> getLogs(@RequestParam("userId") Long userId) {
        return service.findLogsByUser(userId);
    }

    @DeleteMapping("/logs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLog(@PathVariable Long id) {
        service.deleteLog(id);
    }
}
