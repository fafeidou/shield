package com.example.kafka.autoconfig;

import lombok.Data;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

@Data
public class ConsumerConfigWrapper extends KafkaProperties.Consumer {
    private KafkaProperties.Listener listener;

    private Integer concurrency;
}
