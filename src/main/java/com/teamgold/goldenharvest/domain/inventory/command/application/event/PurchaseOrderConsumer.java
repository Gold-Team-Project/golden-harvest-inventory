package com.teamgold.goldenharvest.domain.inventory.command.application.event;

import com.teamgold.goldenharvest.common.idempotent.BloomFilterManager;
import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.PurchaseOrderCreatedEvent;
import com.teamgold.goldenharvest.domain.inventory.command.infrastructure.InboundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class PurchaseOrderConsumer {

    private final ApplicationEventPublisher eventPublisher;
    private final InboundRepository inboundRepository;
    private final BloomFilterManager filter;


    /* purchase order 이벤트를 가장 앞단에서 수신하는 kafka listener이다
     * 이벤트 수신 로그 출력 후 spring eventListener로 이벤트를 relay한다
     */
    @Transactional
    @KafkaListener(topics = "purchase.order.created", groupId = "golden.harvest.inventory.processor")
    public void consume(PurchaseOrderCreatedEvent event) {
        log.info("purchase.order.created event consuming");

        if (!filter.isFirstRequest("purchase.order.created", event.purchaseOrderId())) {
            if (Objects.nonNull(inboundRepository.findByPurchaseOrderItemId(event.purchaseOrderId()))) {
                log.info("구매 주문 중복 감지됨");

                return;
            }
        }

        eventPublisher.publishEvent(event);
    }
}
