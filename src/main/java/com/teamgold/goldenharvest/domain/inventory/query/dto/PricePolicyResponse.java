package com.teamgold.goldenharvest.domain.inventory.query.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class PricePolicyResponse {
	String skuNo;
	BigDecimal marginRate;
	Boolean isActive;
}
