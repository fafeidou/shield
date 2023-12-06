package com.example.springstudy.lifecycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/*
    bean 的生命周期
 */
@SpringBootApplication
public class LifeCycleApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LifeCycleApplication.class, args);
        context.close();
    }
}
