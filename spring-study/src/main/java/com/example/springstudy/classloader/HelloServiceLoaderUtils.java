package com.example.springstudy.classloader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * java -jar arthas-study-0.0.1-SNAPSHOT.jar
 *
 * sc -d com.example.springstudy.classloader.HelloService
 * 获取到的是 org.springframework.boot.loader.LaunchedURLClassLoader
 *
 * 做法就是将ClassLoader.getSystemClassLoader()改成
 * Thread.currentThread().getContextClassLoader()即可
 *
 * SpringBoot-Jar包启动流程 https://blog.51cto.com/u_15401512/4309352
 * SpringBoot直接启动与jar包启动的不同 https://blog.csdn.net/weixin_45673552/article/details/118732918
 * 深入理解SpringBoot加载FatJar原理  https://juejin.cn/post/7275225948453437480
 */
@Component
public class HelloServiceLoaderUtils implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public String invoke(String className) {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
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