package com.study.arthas;

public class LoopDemo {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                System.out.println("1111111");
            }
        }, "login-thread").start();
    }
}
