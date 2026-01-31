package com.teamgold.goldenharvest.domain.inventory.command.application.event;

import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.ItemMasterUpdatedEvent;
import com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.ItemOriginPriceUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemMasterConsumer {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @KafkaListener(topics = "item.master.updated", groupId="golden.harvest.inventory.processor")
    public void consumeItemMasterEvent(ItemMasterUpdatedEvent event) {
        log.info("item.master.updated event consuming");

        eventPublisher.publishEvent(event);
    }

    @Transactional
    @KafkaListener(topics = "item.origin.price.updated", groupId = "golden.harvest.inventory.processor")
    public void consumeOriginPriceEvent(ItemOriginPriceUpdatedEvent event) {
        log.info("item.origin.price.updated event consuming");

        eventPublisher.publishEvent(event);
    }
}
