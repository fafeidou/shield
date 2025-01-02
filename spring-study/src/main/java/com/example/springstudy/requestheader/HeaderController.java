package com.example.springstudy.requestheader;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HeaderController {

    @GetMapping("/context")
    public ResponseEntity<String> getContext() {
        // 获取请求上下文
        RequestContext context = RequestContextHolder.getContext();

        if (context != null) {
            return ResponseEntity.ok("User-Agent: " + context.getUserAgent() + ", Request-ID: " + context.getRequestId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request context is not available");
        }
    }
}

