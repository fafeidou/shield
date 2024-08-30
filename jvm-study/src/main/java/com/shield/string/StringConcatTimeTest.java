package com.shield.string;

import org.junit.Test;

public class StringConcatTimeTest {

    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        String src = "";
        for (int i = 0; i < 100000; i++) {
            src = src + "a";
        }
        //每次循环都会创建一个StringBuilder、String
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为:" + (end - start));
        //花费的时间为:4092ms
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("占用的内存:" + (totalMemory - freeMemory));
        // 占用的内存:18042600B
    }

    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            src.append("a");
        }
        //每次循环都会创建一个StringBuilder、String
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为:" + (end - start));
        //花费的时间为:8ms
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("占用的内存:" + (totalMemory - freeMemory));
        // 占用的内存:11038776B
    }


}

