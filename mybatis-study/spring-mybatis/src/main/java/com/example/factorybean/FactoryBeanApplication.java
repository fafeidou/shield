package com.example.factorybean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FactoryBeanApplication implements CommandLineRunner {
    //@Autowired
    //private ApplicationContext applicationContext;
    //
    //@Autowired
    //private CustomerFactoryBean customerFactoryBean;

    @Autowired
    private MyBean myBean;
    public static void main(String[] args) {
        SpringApplication.run(FactoryBeanApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Object customer = applicationContext.getBean("customerFactoryBean");
        //System.out.println(customer);
        System.out.println(myBean);
        //System.out.println(customerFactoryBean);
    }
}