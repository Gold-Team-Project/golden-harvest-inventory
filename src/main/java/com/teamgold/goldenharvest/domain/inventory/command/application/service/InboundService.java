package com.teamgold.goldenharvest.domain.inventory.command.application.service;

import com.teamgold.goldenharvest.common.exception.BusinessException;
import com.teamgold.goldenharvest.common.exception.ErrorCode;
import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.PurchaseOrderCreatedEvent;
import com.teamgold.goldenharvest.domain.inventory.command.domain.lot.Inbound;
import com.teamgold.goldenharvest.domain.inventory.command.infrastructure.IdGenerator;
import com.teamgold.goldenharvest.domain.inventory.command.infrastructure.InboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InboundService {

	private final InboundRepository inboundRepository;
	private final LotService lotService;

	@Transactional
	public String processInbound(PurchaseOrderCreatedEvent purchaseOrderEvent) {

		if (inboundRepository.findByPurchaseOrderItemId(purchaseOrderEvent.purchaseOrderId()).isPresent()) {
				throw new BusinessException(ErrorCode.DUPLICATE_REQUEST);
		}


		// 입고 ID 생성
		String inboundNo = IdGenerator.createId("in");

		// 입고 데이터 구성
		Inbound inbound = Inbound.builder()
				.inboundId(inboundNo)
				.purchaseOrderItemId(purchaseOrderEvent.purchaseOrderId())
				.skuNo(purchaseOrderEvent.skuNo())
				.quantity(purchaseOrderEvent.quantity())
				.inboundDate(LocalDate.now())
			.build();

		inboundRepository.save(inbound);

		return lotService.createLot(purchaseOrderEvent, inbound.getInboundId());
	}
}
