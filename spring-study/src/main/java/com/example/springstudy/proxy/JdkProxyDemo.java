package com.example.springstudy.proxy;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * jad com.example.springstudy.proxy.$Proxy0
 *
 *- JDK 动态代理，通过继承 Proxy 类，然后实现被代理类的的接口，来生成代理对象；
 * - 调用时，在实现的接口方法里面调用父类中 InvocationHandler 的 invoke 方法；
 * - 在接口 InvocationHandler 的实现类里面定义具体的调用逻辑，通过接口回调将【增强逻辑】置于代理类之外，这里就可以增加额外的功能，对目标方法进行增强。
 * - 配合接口方法反射，就可以再联动调用目标方法
 * - 限制⛔：代理增强是借助多态来实现，因此静态方法、final 方法均不能通过代理实现
 */
public class JdkProxyDemo {

    interface Foo {
        void foo();
    }

    static final class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("target foo");
        }
    }

    // jdk 只能针对接口代理
    // cglib
    public static void main(String[] param) throws IOException {
        // 目标对象
        Target target = new Target();

        ClassLoader loader = JdkProxyDemo.class.getClassLoader(); // 用来加载在运行期间动态生成的字节码
        Foo proxy = (Foo) Proxy.newProxyInstance(loader, new Class[]{Foo.class}, (p, method, args) -> {
            System.out.println("before...");
            // 目标.方法(参数)
            // 方法.invoke(目标, 参数);
            Object result = method.invoke(target, args);
            System.out.println("after....");
            return result; // 让代理也返回目标方法执行的结果
        });

        System.out.println(proxy.getClass());

        proxy.foo();

        System.in.read();
    }
}