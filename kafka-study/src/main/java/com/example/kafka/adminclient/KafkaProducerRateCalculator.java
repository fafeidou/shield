package com.example.kafka.adminclient;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class KafkaProducerRateCalculator {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Kafka 配置
        String bootstrapServers = "localhost:9092";  // Kafka 的 bootstrap server 地址
        String topicName = "your-topic";  // Kafka topic 名称
        int intervalInSeconds = 1; // 时间间隔（秒）

        // 创建 AdminClient 配置
        Properties adminProps = new Properties();
        adminProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        AdminClient adminClient = AdminClient.create(adminProps);

        // 获取 Topic 和 Partition 列表
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Collections.singletonList(topicName));
        Map<String, TopicDescription> topicDescriptions = describeTopicsResult.all().get();
        TopicDescription topicDescription = topicDescriptions.get(topicName);
        List<TopicPartition> topicPartitions = new ArrayList<>();

        for (TopicPartitionInfo partitionInfo : topicDescription.partitions()) {
            topicPartitions.add(new TopicPartition(topicName, partitionInfo.partition()));
        }

        // 获取分区的最新日志结束偏移量
        Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> latestOffsets = adminClient.listOffsets(
                topicPartitions.stream().collect(Collectors.toMap(tp -> tp, tp -> OffsetSpec.latest()))
        ).all().get();

        // 记录当前时间的偏移量
        Map<TopicPartition, Long> initialOffsets = new HashMap<>();
        for (TopicPartition partition : topicPartitions) {
            ListOffsetsResult.ListOffsetsResultInfo offsetInfo = latestOffsets.get(partition);
            initialOffsets.put(partition, offsetInfo.offset());
        }

        // 等待一段时间后重新获取偏移量，计算生产速度
        TimeUnit.SECONDS.sleep(intervalInSeconds);

        // 获取新的日志结束偏移量
        Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> newOffsets = adminClient.listOffsets(
                topicPartitions.stream().collect(Collectors.toMap(tp -> tp, tp -> OffsetSpec.latest()))
        ).all().get();

        long totalMessagesProduced = 0;

        for (TopicPartition partition : topicPartitions) {
            long initialOffset = initialOffsets.get(partition);
            long newOffset = newOffsets.get(partition).offset();
            long messagesProducedInInterval = newOffset - initialOffset;
            totalMessagesProduced += messagesProducedInInterval;

            // 输出每个分区的生产速率
            System.out.println("Partition: " + partition.partition() + ", Messages Produced: " + messagesProducedInInterval);
        }

        // 输出总生产速率
        double productionRate = totalMessagesProduced / (double) intervalInSeconds;
        System.out.println("Total Messages Produced: " + totalMessagesProduced);
        System.out.println("Production Rate: " + productionRate + " messages/sec");

        // 关闭 AdminClient
        adminClient.close();
    }
}
