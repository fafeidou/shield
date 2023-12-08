package com.example.springstudy.springmvc;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对应的是SimpleControllerHandlerAdapter
 */
@org.springframework.stereotype.Controller("/demo1controller")
public class Demo1Controller implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        System.out.println("this my demo controller~");
        response.getWriter().write("this my demo controller from body");
        // 返回null告诉视图渲染  直接把body里面的内容输出浏览器即可
        return null;
    }
}
