package com.example.agent;

public class TestMain {

    static {
        System.out.println("TestMain static block run...");
    }

    public static void main(String[] args) {
        System.out.println("TestMain main start...");
        try {
            for (int i = 0; i < 100; i++) {
                test();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TestMain main end...");
    }

    public static void test() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("TestMain main running...");
    }
}