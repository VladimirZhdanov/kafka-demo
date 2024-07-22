package com.homel.kafka.exeption;

import org.slf4j.Logger;

public class KafkaConnectionException extends RuntimeException {
    public KafkaConnectionException(Logger logger, String message, Throwable exception) {
        logger.error(message, exception);
    }
}
