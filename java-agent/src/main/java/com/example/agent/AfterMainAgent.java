package com.example.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class AfterMainAgent {
    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        System.out.println("AfterMainAgent agentArgs: " + agentArgs);

        Class<?>[] classes = instrumentation.getAllLoadedClasses();
        for (Class<?> cls : classes) {
            System.out.println("AfterMainAgent get loaded class: " + cls.getName());
        }

        instrumentation.addTransformer(new DefineTransformer(), true);
        instrumentation.retransformClasses(TestMain.class);
    }

    static class DefineTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("AfterMainAgent transform Class:" + className);
            return classfileBuffer;
        }
    }
}
