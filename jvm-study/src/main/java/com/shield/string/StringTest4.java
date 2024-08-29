package com.shield.string;

import org.junit.Test;

public class StringTest4 {
    @Test
    public void test1() {
        String s1 = "hello";//code (1)
        String s2 = "hello";//code(2)
        String s3 = "atguigu";//code (3)
        System.out.println();
    }

    @Test
    public void test2() {
        String s1 = new String("hello");//code(4)
        String s2 = new String("hello");//code(5)
    }
}
