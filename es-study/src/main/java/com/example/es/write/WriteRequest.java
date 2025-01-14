package com.example.es.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

@Slf4j
public class WriteRequest {
    /**
     * 写入失败重试
     * @param bulkRequest
     * @param maxRetries
     * @throws IOException
     */
    public void bulkIndexWithRetry(BulkRequest bulkRequest, int maxRetries) throws IOException {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(""));
        int retryCount = 0;
        BulkRequest retryBulkRequest = new BulkRequest();

        while (retryCount < maxRetries) {
            if (retryCount > 0) {
                log.info("Retrying bulk request, attempt {} of {}", retryCount, maxRetries);
                try {
                    Thread.sleep(1000 * (long) Math.pow(2, retryCount)); // 指数退避
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Retry interrupted: {}", e.getMessage());
                    break;
                }
            }

            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (!bulk.hasFailures()) {
                log.info("Bulk request succeeded.");
                return;
            }

            retryBulkRequest = new BulkRequest();
            for (BulkItemResponse item : bulk.getItems()) {
                if (item.isFailed()) {
                    DocWriteRequest<?> failedRequest = bulkRequest.requests().get(item.getItemId());
                    String s = ((IndexRequest) failedRequest).source().utf8ToString();
                    retryBulkRequest.add(failedRequest);
                }
            }

            bulkRequest = retryBulkRequest;
            retryCount++;
        }

        if (retryCount >= maxRetries) {
            log.error("Max retries reached. Failed to index some documents.");
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = Student.builder().name("name").address("beijing").build();
        IndexRequest indexRequest = new IndexRequest("test-index")
                .source(objectMapper.writeValueAsString(student), XContentType.JSON)
                .routing("test-routing")
                .opType(DocWriteRequest.OpType.INDEX);

        String source = indexRequest.source().utf8ToString();
        String index = indexRequest.index();
        String routing = indexRequest.routing();
        //根据string
        System.out.println(source);
    }

    @Data
    @Builder
    public static class Student {
        private String name;
        private String address;
    }

}
