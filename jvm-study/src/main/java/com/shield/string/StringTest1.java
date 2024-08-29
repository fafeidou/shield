package com.shield.string;

import org.junit.Test;

public class StringTest1 {

    @Test
    public void test01() {
        String sl = "java";
        String s2 = "java";
        sl = "atguigu";
        System.out.println(sl);//atguigu
        System.out.println(s2);//java
    }

    @Test
    public void test2() {
        String sl = "java";
        String s2 = sl + "atguigu";
        System.out.println(sl);//java
        System.out.println(s2);//javaatquigu
    }

    @Test
    public void test3() {
        String sl = "java";
        String s2 = sl.concat("atguigu");
        System.out.println(sl);//java
        System.out.println(s2);//javaatguigu
    }

    @Test
    public void test4() {
        String sl = "java";
        String s2 = sl.replace("a","A");
        System.out.println(sl);//java
        System.out.println(s2);//jAvA
    }


}
