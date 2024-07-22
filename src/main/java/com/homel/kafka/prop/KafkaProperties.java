package com.homel.kafka.prop;

import lombok.Getter;
import lombok.Setter;

/**
 * Настройки kafka файловый обмен
 */
@Getter
@Setter
public class KafkaProperties {
    private String bootstrapServers;
    private String groupId;
    private String topic;
    private String autoOffsetReset;
    private String trustedPackages = "*";
    private int concurrency;
    private int maxPollRecords;
    private int maxPollInternalMs;
}