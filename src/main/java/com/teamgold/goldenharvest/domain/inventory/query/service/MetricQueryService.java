package com.teamgold.goldenharvest.domain.inventory.query.service;

import com.teamgold.goldenharvest.domain.inventory.query.dto.MetricsResponse;
import com.teamgold.goldenharvest.domain.inventory.query.mapper.MetricsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricQueryService {

    private final MetricsMapper metricsMapper;

    public MetricsResponse findLotMetrics() {
        return metricsMapper.getMetrics();
    }
}
