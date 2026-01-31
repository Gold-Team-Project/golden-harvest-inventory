package com.teamgold.goldenharvest.domain.inventory.command.application.service;

import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.SalesOrderCreatedEvent;
import com.teamgold.goldenharvest.domain.inventory.command.domain.lot.Lot;
import com.teamgold.goldenharvest.domain.inventory.command.domain.lot.Outbound;
import com.teamgold.goldenharvest.domain.inventory.command.infrastructure.IdGenerator;
import com.teamgold.goldenharvest.domain.inventory.command.infrastructure.OutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OutboundService {

	private final OutboundRepository outboundRepository;

	@Transactional
	public String processOutbound(Lot lot, int consumedQuantity, SalesOrderCreatedEvent salesOrderEvent) {
		Outbound currentOutbound = Outbound.builder()
			.outboundId(IdGenerator.createId("out"))
			.salesOrderItemId(salesOrderEvent.salesOrderItemId())
			.lot(lot)
			.outboundDate(LocalDate.now())
			.quantity(consumedQuantity)
			.outboundPrice(salesOrderEvent.salesPrice())
			.build();

		return outboundRepository.save(currentOutbound).getOutboundId();
	}
}
