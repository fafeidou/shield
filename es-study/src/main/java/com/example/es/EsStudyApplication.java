package com.example.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class EsStudyApplication implements CommandLineRunner {
    @Resource(name = "testRestHighLevelClient")
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private DefaultListableBeanFactory beanFactory;
    public static void main(String[] args) {
        SpringApplication.run(EsStudyApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Object bean = beanFactory.getBean("testRestHighLevelClient");
        System.out.println(restHighLevelClient);
    }
}
