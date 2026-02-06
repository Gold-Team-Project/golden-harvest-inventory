package com.teamgold.goldenharvest.domain.inventory.command.application.event.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ItemOriginPriceUpdatedEvent(
	String skuNo,
	BigDecimal originPrice
) { }
