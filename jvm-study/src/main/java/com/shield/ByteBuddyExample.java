package com.shield;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class ByteBuddyExample {
    public static void main(String[] args) throws IOException {
        // 初始化字节码增强代理
        ByteBuddyAgent.install();

        // 使用Byte Buddy修改方法
        DynamicType.Loaded<SampleClass> dynamicType = new ByteBuddy()
                .redefine(SampleClass.class)
                .method(ElementMatchers.named("setMessage"))
                .intercept(MethodDelegation.to(Interceptor.class))
                .make()
                .load(SampleClass.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        SampleClass obj = new SampleClass();
        System.out.println("Before modification: " + obj.getMessage());

        obj.setMessage("Attempted Change");
        System.out.println("After modification: " + obj.getMessage());

        dynamicType.saveIn(new File(ByteBuddyExample.class.getResource("/").getPath()));
    }

    public static class Interceptor {
        @RuntimeType
        public static void intercept(@This SampleClass instance, @Argument(0) String message) throws Exception {
            Field field = SampleClass.class.getDeclaredField("message");
            field.setAccessible(true);
            // 修改field的值
            field.set(instance, "Modified by ByteBuddy: " + message);
        }
    }
}
