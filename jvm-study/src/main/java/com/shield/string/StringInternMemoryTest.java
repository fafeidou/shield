package com.shield.string;

public class StringInternMemoryTest {
    static final int MAX_COUNT = 1000 * 10000;
    static final String[] arr = new String[MAX_COUNT];
    static final Integer[] data = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            arr[i] = new String(String.valueOf(data[i % data.length])); //2814ms
            //arr[i] = new String(String.valueOf(data[i % data.length])).intern(); //1282ms
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为:" + (end - start));
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.gc();
        }
    }
}
