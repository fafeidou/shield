package com.example.kafka.adminclient;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class KafkaLagChecker {
    public static void main(String[] args) throws ExecutionException, InterruptedException, NoSuchFieldException, IllegalAccessException {
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

        // 获取 consumer group 成员信息
        DescribeConsumerGroupsResult consumerGroupResult = adminClient.describeConsumerGroups(Collections.singletonList(consumerGroupId));
        Map<String, ConsumerGroupDescription> consumerGroupDescriptionMap = consumerGroupResult.all().get();
        ConsumerGroupDescription consumerGroupDescription = consumerGroupDescriptionMap.get(consumerGroupId);

        // 输出消费者的偏移量与 log-end-offset 比较
        for (TopicPartition partition : topicPartitions) {
            OffsetAndMetadata consumerOffset = consumerOffsets.get(partition);
            if (consumerOffset != null) {
                long consumerOffsetValue = consumerOffset.offset(); // 消费者的当前偏移量

                // 获取 Kafka 中该分区的 log-end-offset
                ListOffsetsResult.ListOffsetsResultInfo logEndOffsetInfo = topicPartitionListOffsetsResultInfoMap.get(partition);
                long logEndOffset = logEndOffsetInfo.offset(); // Kafka 中该分区的 log-end-offset

                // 计算 Lag
                long lag = logEndOffset - consumerOffsetValue;
                String consumerInstance = "";
                // 输出每个消费实例的信息
                for (MemberDescription member : consumerGroupDescription.members()) {
                    for (TopicPartition topicPartition : member.assignment().topicPartitions()) {
                        if (topicPartition.topic().equals(partition.topic())) {
                            Field field = MemberDescription.class.getDeclaredField("memberId");
                            // 设置可以访问私有字段
                            field.setAccessible(true);
                            // 通过反射获取 final 字段的值
                            String memberIdValue = (String) field.get(member);
                            consumerInstance =  memberIdValue + ":" + member.host();
                            break;
                        }
                    }
                }
                // 输出每个分区的 Lag，并输出每个消费者实例信息
                System.out.println("Topic: " + partition.topic() + ", Partition: " + partition.partition() +
                        ", Consumer Offset: " + consumerOffsetValue + ", Log End Offset: " + logEndOffset + ", Lag: " + lag + ", consumerInstance : " + consumerInstance);

            } else {
                System.out.println("No consumer offset found for partition: " + partition);
            }
        }

        // 关闭 AdminClient
        adminClient.close();
    }
}

