package com.homel.kafka.runner;

import com.homel.kafka.model.TestDto;
import com.homel.kafka.producer.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Sender  implements ApplicationListener<ContextRefreshedEvent> {

    private final KafkaPublisher kafkaPublisher;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        TestDto dto = new TestDto();
        kafkaPublisher.sendDeduplication(dto);
    }
}
