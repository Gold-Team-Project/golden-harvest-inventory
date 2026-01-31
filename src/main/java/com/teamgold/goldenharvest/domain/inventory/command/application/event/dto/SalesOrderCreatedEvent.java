package com.teamgold.goldenharvest.domain.inventory.command.application.event.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SalesOrderCreatedEvent(
	String salesOrderItemId,
	String skuNo,
	BigDecimal salesPrice,
	Integer quantity
) { }
