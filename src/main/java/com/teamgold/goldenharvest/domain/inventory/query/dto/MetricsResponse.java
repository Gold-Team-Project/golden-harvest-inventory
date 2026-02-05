package com.teamgold.goldenharvest.domain.inventory.query.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MetricsResponse {
    Long availableQuantity;
    Integer availableLot;
    Integer inbounds;
    Integer outbounds;
    Integer expireDueLot;
    Long discardQuantity;
}
