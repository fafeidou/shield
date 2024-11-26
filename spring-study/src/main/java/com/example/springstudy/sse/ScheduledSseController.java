package com.example.springstudy.sse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
public class ScheduledSseController {

    private final SseEmitter emitter = new SseEmitter();

    @Scheduled(fixedRate = 5000)  // 每5秒发送一个事件
    public void sendScheduledEvent() {
        try {
            emitter.send("data: Scheduled Event at " + System.currentTimeMillis() + "\n\n");
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}

