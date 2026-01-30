package com.teamgold.goldenharvest.domain.inventory.query.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class OutboundResponse {
	String outboundId;
	String salesOrderItemId;
	String lotNo;
	String skuNo;
	LocalDate outboundDate;
	Integer quantity;
	BigDecimal outboundPrice;
}
