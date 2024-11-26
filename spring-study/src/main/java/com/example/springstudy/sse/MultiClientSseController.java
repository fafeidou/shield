package com.example.springstudy.sse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class MultiClientSseController {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/sse/v2")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        // 在新线程中发送数据
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    for (SseEmitter e : emitters) {
                        e.send("data: Event " + i + "\n\n");
                    }
                    Thread.sleep(1000); // 每秒发送一次
                }
            } catch (Exception e) {
                emitters.forEach(t -> t.completeWithError(e));
            }
        }).start();

        return emitter;
    }
}

