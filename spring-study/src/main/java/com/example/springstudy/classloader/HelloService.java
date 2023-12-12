package com.example.springstudy.classloader;

import org.springframework.stereotype.Service;

/**
 * @author batman
 */
@Service
public class HelloService {

    private String name;

    public String hello(){
        return "hello loader";
    }

}
