package com.teamgold.goldenharvest.common.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@Profile("local")
public class KafkaLocalConfig {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "golden.harvest.inventory.processor");
        props.put("spring.json.use.type.headers", true);

        String typeMapping =
                "SalesOrderCreatedEvent:com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.SalesOrderCreatedEvent, " +
                        "PurchaseOrderCreatedEvent:com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.PurchaseOrderCreatedEvent, " +
                        "ItemMasterUpdatedEvent:com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.ItemMasterUpdatedEvent, " +
                        "ItemOriginPriceUpdatedEvent:com.teamgold.goldenharvest.domain.inventory.command.application.event.dto.ItemOriginPriceUpdatedEvent";
        props.put("spring.json.type.mapping", typeMapping);

        props.put("spring.json.trusted.packages", "*");

        StringDeserializer keyDeserializer = new StringDeserializer();

        JsonMapper jsonMapper = JsonMapper.builder().build();

        JacksonJsonDeserializer<Object> jacksonDeserializer =
                new JacksonJsonDeserializer<>(Object.class, jsonMapper);

        ErrorHandlingDeserializer<Object> valueDeserializer =
                new ErrorHandlingDeserializer<>(jacksonDeserializer);

        return new DefaultKafkaConsumerFactory<>(
                props,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        StringSerializer keySerializer = new StringSerializer();

        JsonMapper jsonMapper = JsonMapper.builder().build();
        JacksonJsonSerializer<Object> valueSerializer = new JacksonJsonSerializer<>(jsonMapper);

        return new DefaultKafkaProducerFactory<>(props, keySerializer, valueSerializer);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}