package com.study.arthas.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class HelloServiceLoaderUtils implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public String invoke(String className) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            System.out.println("classLoader is : " + classLoader);
            Class clz = classLoader.loadClass(className);
            Object bean = applicationContext.getBean(clz);
            Method method = clz.getMethod("hello");
            return (String) method.invoke(bean);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}