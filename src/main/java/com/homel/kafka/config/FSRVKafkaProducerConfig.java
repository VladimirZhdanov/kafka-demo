package com.homel.kafka.config;

import com.homel.kafka.prop.KafkaProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * Config class for frsv kafka consumer.
 */
@Slf4j
@EnableKafka
@Configuration
@ConditionalOnProperty(name = "fsrv.kafka.enable", havingValue = "true")
public class FSRVKafkaProducerConfig {
    private final KafkaProperties kafkaProperties;

    public FSRVKafkaProducerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaTemplate<String, Object> deduplicationKafkaTemplate() {
        return new KafkaTemplate<>(buildKafkaProducerFactory());
    }

    private DefaultKafkaProducerFactory<String, Object> buildKafkaProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getTrustedPackages());

        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
