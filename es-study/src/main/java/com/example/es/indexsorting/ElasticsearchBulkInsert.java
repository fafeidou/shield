package com.example.es.indexsorting;

import com.google.gson.JsonObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ElasticsearchBulkInsert {

    public static void main(String[] args) {
        // 创建Elasticsearch客户端
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("192.168.56.112", 9200, "http"));
        RestHighLevelClient client = new RestHighLevelClient(builder);

        // 批量插入数据
        try {
            bulkInsert(client, "my_index", 50_000_0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void bulkInsert(RestHighLevelClient client, String index, int numRecords) throws IOException {
        BulkRequest request = new BulkRequest();
        BulkRequest request2 = new BulkRequest();
        Random random = new Random();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numRecords; i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("timestamp", formatTimestamp(getRandomTimestamp())); // 当前时间戳
            jsonObject.addProperty("id", random.nextInt(1_000_000));         // 随机整型ID
            jsonObject.addProperty("long_text", generateRandomString(500));  // 随机长字符串

            IndexRequest indexRequest = new IndexRequest(index)
                    .id(String.valueOf(i)) // 使用自增ID作为文档ID
                    .source(jsonObject.toString(), XContentType.JSON);

            IndexRequest indexRequest2 = new IndexRequest("my_index_2")
                    .id(String.valueOf(i)) // 使用自增ID作为文档ID
                    .source(jsonObject.toString(), XContentType.JSON);

            request.add(indexRequest);
            request2.add(indexRequest2);

            // 每1000条数据提交一次
            if (i % 1000 == 0 && i > 0) {
                BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
                BulkResponse bulkResponse2 = client.bulk(request2, RequestOptions.DEFAULT);
                if (bulkResponse.hasFailures()) {
                    System.out.println("Bulk insert failed: " + bulkResponse.buildFailureMessage());
                }
                if (bulkResponse2.hasFailures()) {
                    System.out.println("Bulk insert my_index_2 failed: " + bulkResponse.buildFailureMessage());
                }
                request = new BulkRequest(); // 重置请求
                request2 = new BulkRequest(); // 重置请求
                System.out.println("Inserted " + i + " records");
            }
        }

        // 提交剩余的数据
        if (request.numberOfActions() > 0) {
            BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                System.out.println("Bulk insert failed: " + bulkResponse.buildFailureMessage());
            }
            System.out.println("Inserted all records");
        }

        if (request2.numberOfActions() > 0) {
            BulkResponse bulkResponse = client.bulk(request2, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                System.out.println("Bulk insert my_index_2 failed: " + bulkResponse.buildFailureMessage());
            }
            System.out.println("Inserted my_index_2 all records");
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Total time taken: " + duration + " ms");
    }

    public static String formatTimestamp(long timestamp) {
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        String zoneId = "Asia/Shanghai";
        // 将时间戳转换为Instant
        Instant instant = Instant.ofEpochMilli(timestamp);

        // 将Instant转换为指定时区的ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of(zoneId));

        // 定义格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // 格式化为字符串
        return formatter.format(zonedDateTime);
    }

    /**
     * 生成随机长字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    /**
     * 获取当前时间的前五天到未来五天之间的随机时间戳
     *
     * @return 随机时间戳（毫秒）
     */
    public static long getRandomTimestamp() {
        Random random = new Random();
        long currentTime = System.currentTimeMillis(); // 当前时间戳
        long fiveDaysInMillis = TimeUnit.DAYS.toMillis(5); // 五天的毫秒数

        // 计算时间范围
        long startRange = currentTime - fiveDaysInMillis; // 前五天
        long endRange = currentTime + fiveDaysInMillis;   // 未来五天

        // 生成随机时间戳
        return startRange + (long) (random.nextDouble() * (endRange - startRange));
    }
}
