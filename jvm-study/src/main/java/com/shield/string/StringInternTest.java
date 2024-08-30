package com.shield.string;

import org.junit.Test;

public class StringInternTest {
    @Test
    public void testl() {
        String s = "ab";
        String s1 = new String("a") + new String("b");
        String s2 = s1.intern();
        System.out.println(s1 == s);//JDK6、JDK7和JDK8:false
        System.out.println(s2 == s);//JDK7和JDK7和JDK8:true
    }

    @Test
    public void test2() {
        String s1 = new String("a") + new String("b");
        String s2 = s1.intern();
        String s = "ab";
        System.out.println(s1 == s);//JDK6:false JDK7和JDK8:true
        System.out.println(s2 == s);//JDK6:true JDK7和JDK8:true
    }
}
