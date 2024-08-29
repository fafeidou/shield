package com.shield.string;

import org.junit.Test;

public class StringTest5 {
    @Test
    public void test1() {
        String s1 = "a" + "b" + "c"; // 编译期优化:等同于 "abc"
        String s2 = "abc"; // "abc"一定是放在StringTable中
        System.out.println(s1 == s2);//true
    }

    @Test
    public void test2() {
        String s1 = "javaEE";
        String s2 = "hadoop";
        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + new String("hadoop");
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;
        //"+"拼接中出现字符串变量等非字面常量
        //结果都不在StringTable中
        System.out.println(s3 == s4);//false
        System.out.println(s3 == s5);//false
        System.out.println(s3 == s6);//false
        System.out.println(s3 == s7);//false
        System.out.println(s5 == s6);//false
        System.out.println(s5 == s7);//false
        System.out.println(s6 == s7);//false
    }

    @Test
    public void test3() {
        String sl = "javaEE";
        String s2 = "hadoop";
        String s3 = "javaEEhadoop";
        String s4 = sl.concat(s2);
        //concat拼接结果不在StringTable中
        System.out.println(s3 == s4);//false
    }

    @Test
    public void test4() {
        String s1 = "hello";
        String s2 = "java";
        String s3 = "hellojava";
        String s4 = (s1 + s2).intern();
        String s5 = s1.concat(s2).intern();
        //拼接后调用intern()方法,结果都在StringTable中
        System.out.println(s3 == s4);
        System.out.println(s3 == s5);
    }

    @Test
    public void test5() {
        final String s1 = "hello";
        final String s2 = "java";
        String s3 = "hellojava";
        String s4 = s1 + s2;
        System.out.println(s3 == s4);//true
    }

}
