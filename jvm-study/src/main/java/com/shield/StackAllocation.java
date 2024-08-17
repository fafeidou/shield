package com.shield;

/**
 * 栈上分配测试
 * -Xmx1G -Xms1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 */
public class StackAllocation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }

        //查看执行时间
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为:" + (end - start) + "ms");
        //为了方便查看堆内存中对象个数,线程sleep
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException el) {
            el.printStackTrace();
        }


    }

    private static void alloc() {
        //未发生逃逸
        User user = new User();

    }

    static class User {

    }
}
