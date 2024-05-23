package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class KafkaConsumer {

    // 自定义主题名称，这里要注意的是主题名称中不能包含特殊符号：“.”、“_”
    public static final String TOPIC_NAME="topic0";

    @KafkaListener(topics = TOPIC_NAME, groupId = "ONE")
    public void topic_one(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("消费者组One消费了消息：Topic:" + topic + ",Record:" + record + ",Message:" + msg);
        }
    }
}
