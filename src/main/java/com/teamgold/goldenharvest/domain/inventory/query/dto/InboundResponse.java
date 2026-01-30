package com.teamgold.goldenharvest.domain.inventory.query.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class InboundResponse {
	String inboundId;
	String purchaseOrderId;
	String skuNo;
	Integer quantity;
	LocalDate inboundDate;
}
