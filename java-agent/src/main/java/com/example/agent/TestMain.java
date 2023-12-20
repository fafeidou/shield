package com.example.agent;

import org.apache.log4j.Logger;

public class TestMain {
    public static Logger LOGGER = Logger.getLogger(TestMain.class);

    static {
        System.out.println("TestMain static block run...");
    }

    public static void main(String[] args) {
        LOGGER.info("TestMain main start...");
        try {
            for (int i = 0; i < 100; i++) {
                test();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("TestMain main end...");
    }

    public static void test() throws InterruptedException {
        Thread.sleep(3000);
        LOGGER.info("TestMain main running...");
    }
}