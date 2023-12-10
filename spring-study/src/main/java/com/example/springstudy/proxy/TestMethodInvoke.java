package com.example.springstudy.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 1. 前 16 次反射性能较低
 * 2. 第 17 次调用会生成代理类，优化为非反射调用，生成字节码直接调用
 * jad sun.reflect.GeneratedMethodAccessor2
 */
// 运行时请添加
public class TestMethodInvoke {
    public static void main(String[] args) throws Exception {
        Method foo = TestMethodInvoke.class.getMethod("foo", int.class);
        for (int i = 1; i <= 17; i++) {
            show(i, foo);
            foo.invoke(new TestMethodInvoke(), i);
        }
        System.in.read();
    }

    // 方法反射调用时, 底层 MethodAccessor 的实现类
    private static void show(int i, Method foo) throws Exception {
        Method getMethodAccessor = Method.class.getDeclaredMethod("getMethodAccessor");
        getMethodAccessor.setAccessible(true);
        Object invoke = getMethodAccessor.invoke(foo);
        if (invoke == null) {
            System.out.println(i + ":" + null);
            return;
        }
        Field delegate = Class.forName("sun.reflect.DelegatingMethodAccessorImpl").getDeclaredField("delegate");
        delegate.setAccessible(true);
        System.out.println(i + ":" + delegate.get(invoke));
    }

    public void foo(int i) {
        System.out.println(i + ":" + "foo");
    }
}
