package com.example.kafka.file;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SendFileToKafka {

    public static void main(String[] args) {

        String filePath = "com/example/kafka/file/ConsumerFileByteArrayFromKafka.java";

        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", "192.168.56.112:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(kafkaProps);
        InputStream in = SendFileToKafka.class.getResourceAsStream("/com/example/kafka/file/ConsumerFileByteArrayFromKafka.java");
        try {
            byte[] buffer = new byte[in.available()];
            // 读到buffer字节数组中
            in.read(buffer);
            ProducerRecord<String, byte[]> record = new ProducerRecord<>("dataTopic", buffer);
            String header = "aaa";
            record.headers().add("test_header", header.getBytes(StandardCharsets.UTF_8));
            producer.send(record);
            in.close();
            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
