package com.shield;

/**
 * -Xms600M -Xmx600M
 * <p>
 * -XX:NewRatio=2设置新生代与老年代的比例。默认值是2.
 * -XX:SurvivorRatio=8设置新生代中 Eden区与Survivor区的比例。默认值是8
 */
public class EdenSurvivorTest {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000000);
    }
}
