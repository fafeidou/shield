package com.shield.string;

import java.util.ArrayList;

/**
 * JDK6中:
 * -XX:PermSize=20m -XX:MaxPermSize=20m -Xms128m -Xmx256m
 * JDK7中:
 * -XX:PermSize=20m -XX:MaxPermSize=20m -Xms128m -Xmx256m
 * JDK8中:
 * -XX:MetaspaceSize=20m -XX:MaxMetaspaceSize=20m -Xms128m -Xmx256m
 */
public class StringTest3 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
