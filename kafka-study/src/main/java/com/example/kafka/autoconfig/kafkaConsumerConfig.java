package com.example.kafka.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix = "spring.kafka.multiple.consumer")
@Configuration
@Data
public class kafkaConsumerConfig {
    private Map<String, ConsumerConfigWrapper> factories;
}
