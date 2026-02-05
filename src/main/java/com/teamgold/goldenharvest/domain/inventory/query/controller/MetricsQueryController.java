package com.teamgold.goldenharvest.domain.inventory.query.controller;

import com.teamgold.goldenharvest.common.response.ApiResponse;
import com.teamgold.goldenharvest.domain.inventory.query.service.MetricQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MetricsQueryController {

    private final MetricQueryService metricQueryService;

    @GetMapping("/items/metrics")
    public ResponseEntity<ApiResponse<?>> getLotMetrics() {
        return ResponseEntity.ok(ApiResponse.success(metricQueryService.findLotMetrics()));
    }

}
