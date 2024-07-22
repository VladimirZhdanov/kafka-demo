package com.homel.kafka.consumer;

import com.homel.kafka.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "fsrv.kafka.enable", havingValue = "true")
@RequiredArgsConstructor
public class FSRVKafkaConsumer {

    private final ProcessService processService;

    @KafkaListener(topics = "${fsrv.kafka.topic}", containerFactory = "fsrvConcurrentKafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, byte[]> record) throws InterruptedException {
        log.info("Bfko message with offset: {} received;", record.offset());
        processService.process(record);
    }
}
