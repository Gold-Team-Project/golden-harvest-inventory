package com.teamgold.goldenharvest.domain.inventory.command.application.service;

import com.teamgold.goldenharvest.common.exception.BusinessException;
import com.teamgold.goldenharvest.common.exception.ErrorCode;
import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.ItemMasterUpdatedEvent;
import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.ItemOriginPriceUpdatedEvent;
import com.teamgold.goldenharvest.domain.inventory.command.domain.mirror.ItemMasterMirror;
import com.teamgold.goldenharvest.domain.inventory.command.infrastructure.ItemMasterMirrorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemMasterMirrorService {

	private final ItemMasterMirrorRepository itemMasterMirrorRepository;

	public void updateItemMasterMirror(ItemMasterUpdatedEvent itemMasterUpdatedEvent) {
		ItemMasterMirror itemMasterMirror = ItemMasterMirror.builder()
				.skuNo(itemMasterUpdatedEvent.skuNo())
				.itemName(itemMasterUpdatedEvent.itemName())
				.gradeName(itemMasterUpdatedEvent.gradeName())
				.varietyName(itemMasterUpdatedEvent.varietyName())
				.baseUnit(itemMasterUpdatedEvent.baseUnit())
				.isActive(itemMasterUpdatedEvent.isActive())
				.fileUrl(itemMasterUpdatedEvent.fileUrl())
				.build();

		itemMasterMirrorRepository.save(itemMasterMirror);
	}

	public void updateOriginPrice(ItemOriginPriceUpdatedEvent itemOriginPriceUpdateEvent) {
		List<ItemMasterMirror> itemMasterMirrors = itemMasterMirrorRepository.findBySkuNo(
			itemOriginPriceUpdateEvent.skuNo());

//		if (!itemOriginPriceUpdateEvent.updatedDate().equals(LocalDate.now())) {
//			throw new BusinessException(ErrorCode.INVALID_REQUEST);
//		} // 원가 정보가 당일 데이터가 아닌 경우 오류

		if (itemMasterMirrors.size() != 1) {
			throw new BusinessException(ErrorCode.INVALID_REQUEST);
		} // 원가 정보 업데이트 대상 상품이 1개가 아닐 경우 오류

		ItemMasterMirror itemMasterMirror = itemMasterMirrors.getFirst();

		itemMasterMirror.updatePrice(itemOriginPriceUpdateEvent.originPrice());
	}
}
