package com.study.arthas.service;

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
