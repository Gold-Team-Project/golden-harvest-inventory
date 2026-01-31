package com.teamgold.goldenharvest.domain.inventory.command.application.event;

import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.SalesOrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SalesOrderConsumer {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @KafkaListener(topics = "sales.order.created", groupId="golden.harvest.inventory.processor")
    public void consume(SalesOrderCreatedEvent event) {
        log.info("sales.order.created event consuming");

        eventPublisher.publishEvent(event);
    }
}
