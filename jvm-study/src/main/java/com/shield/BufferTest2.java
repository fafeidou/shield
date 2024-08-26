package com.shield;

import sun.misc.VM;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * 直接内存内存的OOM: OutOfMemoryError:Direct buffer memory
 *
 * -XX:MaxDirectMemorySize 设置最大直接内存
 * -Xmx60m 设置最大堆内存
 * 默认 MaxDirectMemorySize 等于 Xmx
 */
public class BufferTest2 {
    private static final int BUFFER = 1024 * 1024 * 20;//20MB

    public static void main(String[] args) {
        // 获取Java虚拟机中的Runtime实例
        Runtime runtime = Runtime.getRuntime();

        // 获取JVM的最大内存
        long maxMemory = runtime.maxMemory();
        System.out.println("Max Memory: " + maxMemory + " bytes");

        long maxDirectMemory = VM.maxDirectMemory();
        System.out.println("Max Direct Memory: " + maxDirectMemory + " bytes");
        ArrayList<ByteBuffer> list = new ArrayList<>();
        int count = 0;
        try {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER);
                list.add(byteBuffer);
                count++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            System.out.println(count);
        }
    }
}
