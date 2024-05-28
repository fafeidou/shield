package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaBatchConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaBatchConsumer.class);

    @KafkaListener(id = "consumer1", topics = "topic2", containerFactory = "test-factory")
    public void consume(List<ConsumerRecord<String, String>> record) throws Exception {
        log.info("KafkaBatchConsumer recode size : {} ", record.size());
    }

}
