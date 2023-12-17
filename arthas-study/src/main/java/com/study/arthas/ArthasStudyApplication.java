package com.study.arthas;

import com.study.arthas.service.HelloService;
import com.study.arthas.service.HelloServiceLoaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArthasStudyApplication implements ApplicationRunner {
    @Autowired
    private HelloServiceLoaderUtils helloServiceLoaderUtils;

    public static void main(String[] args) {
        SpringApplication.run(ArthasStudyApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println(helloServiceLoaderUtils.invoke(HelloService.class.getName()));
    }
}
