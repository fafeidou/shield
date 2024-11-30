package com.example.kafka.adminclient;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class KafkaConsumerSpeedCalculator {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Kafka 配置
        String bootstrapServers = "";  // 请根据实际情况调整
        String consumerGroupId = "";  // 请替换为你的 consumer group
        String topicName = ""; // 请替换为你的 Topic 名称

        // 创建 AdminClient
        Properties adminProps = new Properties();
        adminProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        AdminClient adminClient = AdminClient.create(adminProps);

        // 获取 topic 中所有分区
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Collections.singletonList(topicName));
        Map<String, TopicDescription> topicDescriptions = describeTopicsResult.all().get();
        TopicDescription topicDescription = topicDescriptions.get(topicName);
        List<TopicPartition> topicPartitions = new ArrayList<>();

        for (TopicPartitionInfo partitionInfo : topicDescription.partitions()) {
            topicPartitions.add(new TopicPartition(topicName, partitionInfo.partition()));
        }

        // 获取 consumer group 的偏移量
        ListConsumerGroupOffsetsResult offsetsResult = adminClient.listConsumerGroupOffsets(consumerGroupId);
        Map<TopicPartition, OffsetAndMetadata> consumerOffsets = offsetsResult.partitionsToOffsetAndMetadata().get();

        // 获取 topic 分区的 log-end-offset
        Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> topicPartitionListOffsetsResultInfoMap =
                adminClient.listOffsets(topicPartitions.stream().collect(Collectors.toMap(tp -> tp, tp -> OffsetSpec.latest()))).all().get();

        // 计算每秒的消费速度
        long startTime = System.currentTimeMillis();

        // 输出每个分区的消费速度
        for (TopicPartition partition : topicPartitions) {
            OffsetAndMetadata consumerOffset = consumerOffsets.get(partition);
            if (consumerOffset != null) {
                long consumerOffsetValue = consumerOffset.offset(); // 消费者的当前偏移量

                // 获取 Kafka 中该分区的 log-end-offset
                ListOffsetsResult.ListOffsetsResultInfo logEndOffsetInfo = topicPartitionListOffsetsResultInfoMap.get(partition);
                long logEndOffset = logEndOffsetInfo.offset(); // Kafka 中该分区的 log-end-offset

                // 计算滞后（lag）
                long lag = logEndOffset - consumerOffsetValue;

                // 输出每个分区的消费速度
                long currentTime = System.currentTimeMillis();
                long timeElapsed = currentTime - startTime;  // 计算时间间隔（毫秒）

                // 计算消费的消息数（消费的消息数量）
                long messagesConsumed = consumerOffsetValue;

                // 计算消费速度：每秒消费的消息数
                double consumptionRate = (double) messagesConsumed / (timeElapsed / 1000.0);  // 每秒消费的消息数

                System.out.println("Partition: " + partition.partition() +
                        ", Consumer Offset: " + consumerOffsetValue +
                        ", Log End Offset: " + logEndOffset +
                        ", Lag: " + lag +
                        ", Consumption Rate: " + consumptionRate + " messages/sec");

            } else {
                System.out.println("No consumer offset found for partition: " + partition);
            }
        }

        // 关闭 AdminClient
        adminClient.close();
    }
}

