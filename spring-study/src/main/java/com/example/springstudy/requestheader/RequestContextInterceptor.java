package com.example.springstudy.requestheader;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 创建上下文对象
        RequestContext context = new RequestContext();

        // 从请求头中获取信息并设置到上下文
        context.setUserAgent(request.getHeader("User-Agent"));
        context.setRequestId(request.getHeader("X-Request-ID"));

        // 将上下文放到ThreadLocal中
        RequestContextHolder.setContext(context);

        return true; // 继续处理请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 清理ThreadLocal，避免内存泄漏
        RequestContextHolder.clear();
    }
}

