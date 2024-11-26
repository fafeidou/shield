package com.example.springstudy.sse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {

    /**
     * 这个接口会返回一个持续的事件流，浏览器会通过 EventSource 接收
     */
    @GetMapping("/sse")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();

        // 启动一个新的线程模拟定期推送事件
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    // 向客户端发送数据
                    emitter.send("data: Event " + i + "\n\n");
                    Thread.sleep(1000); // 每秒发送一次
                }
                emitter.complete(); // 发送完毕后，标记事件流完成
            } catch (Exception e) {
                emitter.completeWithError(e); // 如果出现异常，标记事件流出错
            }
        }).start();

        return emitter; // 返回 SseEmitter 实例，它会处理异步流式数据
    }
}

