package com.homel.kafka.service;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessService {
    @Async("threadPoolTaskExecutor")
    public void process(ConsumerRecord<String, byte[]> record) throws InterruptedException {
        log.info("Start process bfko message with offset: {};", record.offset());

        Random random = new Random();
        if (random.nextInt(3) == 2) {
            //Теряем данные, нужно думать как не терять
            throw new RuntimeException(String.format("smth wrong, offset: %s", record.offset()));
        }

        Thread.sleep(1000);
        log.info("Finish process bfko message with offset: {};", record.offset());
    }
}
