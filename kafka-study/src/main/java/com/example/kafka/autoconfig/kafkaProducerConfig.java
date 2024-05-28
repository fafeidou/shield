package com.example.kafka.autoconfig;

import lombok.Data;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix = "spring.kafka.multiple.producer")
@Configuration
@Data
public class kafkaProducerConfig {
    private Map<String, KafkaProperties.Producer> templates;
}
