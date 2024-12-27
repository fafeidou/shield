package com.example.proguard.resolve;

import proguard.retrace.FrameInfo;
import proguard.retrace.FramePattern;
import proguard.retrace.FrameRemapper;
import proguard.retrace.ReTrace;

import java.io.File;
import java.io.IOException;

public class CustomizeSymbolAnalysis {
    /**
     * classMap、memberMap 可以存到kv 缓存中
     */

    public static void main(String[] args) throws IOException {
        final FramePattern pattern = new FramePattern(ReTrace.REGULAR_EXPRESSION, false);
        final FrameRemapper frameRemapper = new FrameRemapper();
        CustomizeMappingReader mappingReader = new CustomizeMappingReader(new File("proguard/target/proguard_map.txt"));
        mappingReader.pump(frameRemapper);

        String crashStack = "Caused by: java.lang.RuntimeException: test exception\n" +
                "        at com.example.proguard.b.b.<init>(User.java:25) ~[classes!/:0.0.1]\n" +
                "        at com.example.proguard.ProguardApplication.run(ProguardApplication.java:22) [classes!/:0.0.1]\n" +
                "        at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:804) [spring-boot-2.4.2.jar!/:2.4.2]\n" +
                "        ... 13 common frames omitted\n";
        final String[] stackList = crashStack.split("\n");

        for (String stack : stackList) {
            FrameInfo frameInfo = pattern.parse(stack);
            if (frameInfo == null) {
                System.out.println(stack);
                continue;
            }
            for (FrameInfo retracedFrame : mappingReader.transform(frameInfo)) {
                String retraceStack = pattern.format(stack, retracedFrame);
                System.out.println(retraceStack);
            }
        }
    }
}
