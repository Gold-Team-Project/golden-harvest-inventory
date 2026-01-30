package com.teamgold.goldenharvest.domain.inventory.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor // Changed from protected to public access
@AllArgsConstructor
public class AvailableItemResponse {
	String skuNo;
	Integer quantity;
	String itemName;
	String gradeName;
	String varietyName;
	String baseUnit;
	Double customerPrice;
	String fileUrl;
}
