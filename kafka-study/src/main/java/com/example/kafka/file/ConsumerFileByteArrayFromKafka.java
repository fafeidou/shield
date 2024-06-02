package com.example.kafka.file;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerFileByteArrayFromKafka {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.56.112:9092");
        props.put("group.id", "group1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("dataTopic"));
        try {
            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(100);
                for (ConsumerRecord<String, byte[]> record : records) {
                    Headers headers = record.headers();
                    Iterable<Header> testHeader = headers.headers("test_header");
                    for (Header header : testHeader) {
                        String recordHeader = new String(header.value(), "UTF-8");
                        System.out.println("recordHeader => " + recordHeader);
                    }
                    byte[] message = record.value();
                    System.out.println(new String(message));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
