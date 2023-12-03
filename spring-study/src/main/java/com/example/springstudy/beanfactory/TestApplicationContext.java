package com.example.springstudy.beanfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;

/*
    BeanFactory 与 ApplicationContext 的区别
 */
@SpringBootApplication
public class TestApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(TestApplicationContext.class);

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(TestApplicationContext.class, args);

        /*
            ApplicationContext 比 BeanFactory 多点啥
         */
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        Resource[] resources = context.getResources("classpath:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        System.out.println(context.getEnvironment().getProperty("server.port"));

        context.publishEvent(new UserRegisteredEvent(context));
        context.getBean(Component1.class).register();

    }
}
