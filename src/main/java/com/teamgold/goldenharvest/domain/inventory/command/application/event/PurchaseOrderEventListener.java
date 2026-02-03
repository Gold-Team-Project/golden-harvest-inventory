package com.teamgold.goldenharvest.domain.inventory.command.application.event;

import com.teamgold.goldenharvest.common.broker.KafkaProducerHelper;
import com.teamgold.goldenharvest.common.exception.BusinessException;
import com.teamgold.goldenharvest.common.exception.RetryableExceptions;
import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.PurchaseOrderCreatedEvent;
import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.PurchaseOrderResultEvent;
import com.teamgold.goldenharvest.domain.inventory.command.application.service.InboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class PurchaseOrderEventListener {

	private final InboundService inboundService;
	private final KafkaProducerHelper producer;

	/* PurchaseOrderConsumer에서 trigger한 이벤트를 받아 처리한다
	 * 실패시 retry 및 보상 트랜잭션/이벤트 발행을 구현한다
	 */
	@EventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handlePurchaseOrder(@Valid PurchaseOrderCreatedEvent purchaseOrderCreatedEvent) {
		log.info("구매 주문 이벤트 수신 완료. 객체: {}", purchaseOrderCreatedEvent);

		try {
			String createdLotNo = inboundService.processInbound(purchaseOrderCreatedEvent);

			producer.send(
					"purchase.order.result",
					purchaseOrderCreatedEvent.purchaseOrderId(),
					PurchaseOrderResultEvent.success(purchaseOrderCreatedEvent.purchaseOrderId()),
					null
			);

            log.info("구매 주문 이벤트 처리 성공. Lot 번호: {}", createdLotNo);

		}
		catch (Exception e) {
			log.error("구매 주문 이벤트 처리 중 오류 발생: {}", e.getMessage());

			if (RetryableExceptions.isRetryable(e)) {
				log.warn("Retryable 오류 발생, kafka retry trigger.");
				throw e;
			}
			else {
				log.error("Retry 불가능 오류 발생, 보상 이벤트 발행 및 롤백 시작.");

				producer.send(
						"purchase.order.result",
						purchaseOrderCreatedEvent.purchaseOrderId(),
						PurchaseOrderResultEvent.fail(purchaseOrderCreatedEvent.purchaseOrderId()),
						null
				);

				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

				log.warn("구매 주문 이벤트 처리 실패 트랜잭션 롤백 및 이벤트 발행 완료.");
			}
		}
	}
}