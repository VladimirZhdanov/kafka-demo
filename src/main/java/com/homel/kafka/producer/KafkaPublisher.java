package com.homel.kafka.producer;

import com.homel.kafka.exeption.KafkaConnectionException;
import com.homel.kafka.model.TestDto;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaPublisher {

    private static final String KAFKA_SENT_MESSAGE = "Kafka sent message='{}' with offset={}";
    private static final String KAFKA_UNABLE_TO_SEND_MESSAGE = "Kafka is unable to send message='%s'";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${fsrv.kafka.topic}")
    private String deduplicationTopic;

    public KafkaPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDeduplication(TestDto dto) {
        for (int i = 0; i < 20; i++) {
            dto.setId(UUID.randomUUID().toString());
            send(new ProducerRecord<>(deduplicationTopic, dto));
        }
    }

    public void send(ProducerRecord<String, Object> producerRecord) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        kafkaTemplate.send(producerRecord)
                .addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.debug(KAFKA_SENT_MESSAGE, producerRecord.value(), result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable exception) {
                        future.completeExceptionally(new KafkaConnectionException(log,
                                String.format(KAFKA_UNABLE_TO_SEND_MESSAGE, producerRecord.value()), exception));
                    }
                });
    }

}
