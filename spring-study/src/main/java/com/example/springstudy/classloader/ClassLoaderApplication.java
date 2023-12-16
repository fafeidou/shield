package com.example.springstudy.classloader;

import com.example.springstudy.SpringStudyApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassLoaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }
}
