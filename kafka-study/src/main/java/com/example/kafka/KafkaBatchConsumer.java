package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaBatchConsumer {
    @KafkaListener(id = "consumer1", topics = "topic0", containerFactory = "batchFactory")
    public void consume(List<ConsumerRecord<String, String>> record) throws Exception {
        System.out.println("KafkaBatchConsumer recode size : " + record.size());
    }

}
