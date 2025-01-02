package com.example.springstudy.requestheader;

// 1. 定义上下文对象
public class RequestContext {
    private String userAgent;
    private String requestId;

    // Getter and Setter
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}




