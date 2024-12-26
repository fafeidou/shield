package com.example.proguard.resolve;

import proguard.obfuscate.MappingReader;
import proguard.retrace.FrameInfo;
import proguard.retrace.FramePattern;
import proguard.retrace.FrameRemapper;
import proguard.retrace.ReTrace;

import java.io.File;
import java.io.IOException;

public class CustomizeSymbolAnalysis {
    public static void main(String[] args) throws IOException {
        final FramePattern pattern = new FramePattern(ReTrace.REGULAR_EXPRESSION, false);
        final CustomizeFrameRemapper frameRemapper = new CustomizeFrameRemapper();

        MappingReader mappingReader = new MappingReader(new File("proguard/target/proguard_map.txt"));
        mappingReader.pump(frameRemapper);

        String crashStack = "Caused by: java.lang.RuntimeException: test exception\n" +
                "        at com.example.proguard.a.b.<init>(User.java:25) ~[classes!/:0.0.1]\n" +
                "        at com.example.proguard.ProguardApplication.run(ProguardApplication.java:17)";

        final String[] stackList = crashStack.split("\n");

        for (String stack : stackList) {
            FrameInfo frameInfo = pattern.parse(stack);
            if (frameInfo == null) {
                continue;
            }
            for (FrameInfo retracedFrame : frameRemapper.transform(frameInfo)) {
                String retraceStack = pattern.format(stack, retracedFrame);
                System.out.println(retraceStack);
            }
        }
    }
}
