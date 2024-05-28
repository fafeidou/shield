package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaProducer {

    // 自定义的主题名称
    public static final String TOPIC_NAME="topic2";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * http://localhost:8080/kafka/send?msg=a
     * @param msg
     */
    @RequestMapping("/send")
    public String send(@RequestParam("msg")String msg) {
        log.info("准备发送消息为：{}",msg);
        // 1.发送消息
        ListenableFuture<SendResult<String,String>> future=kafkaTemplate.send(TOPIC_NAME,msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                // 2.发送失败的处理
                log.error("生产者 发送消息失败："+throwable.getMessage());
            }
            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                // 3.发送成功的处理
                log.info("生产者 发送消息成功："+stringObjectSendResult.toString());
            }
        });
        return "接口调用成功";
    }
}
