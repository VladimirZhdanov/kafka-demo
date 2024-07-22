package com.homel.kafka.config;

import com.homel.kafka.prop.KafkaProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES;

/**
 * Config class for frsv kafka consumer.
 */
@Slf4j
@EnableKafka
@Configuration
@ConditionalOnProperty(name = "fsrv.kafka.enable", havingValue = "true")
public class FSRVKafkaConsumerConfig {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> fsrvConcurrentKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(fsrvConsumerFactory(kafkaProperties));
        factory.setConcurrency(kafkaProperties.getConcurrency());
        return factory;
    }

    public ConsumerFactory<String, byte[]> fsrvConsumerFactory(
            KafkaProperties kafkaProperties) {
        Map<String, Object> checkResponseConsumerConfig = fsrvConsumerConfigs(kafkaProperties);
        checkResponseConsumerConfig.put(GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        return new DefaultKafkaConsumerFactory<>(
                checkResponseConsumerConfig, new StringDeserializer(), new ByteArrayDeserializer());
    }

    public Map<String, Object> fsrvConsumerConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        properties.put(AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getAutoOffsetReset());
        properties.put(TRUSTED_PACKAGES, kafkaProperties.getTrustedPackages());
        properties.put(MAX_POLL_RECORDS_CONFIG, kafkaProperties.getMaxPollRecords());
        properties.put(MAX_POLL_INTERVAL_MS_CONFIG, kafkaProperties.getMaxPollInternalMs());
        return properties;
    }
}
