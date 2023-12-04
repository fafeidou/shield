package com.example.springstudy.cyclic;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class ProxyFactory01 {

    public static void main(String[] args) {
        //aspect = 通知(advice) + 切点(pointcut) 一个切面类有一个或者多个通知方法

        //advisor = 更新粒度的切面，包含一个通知和切点

        ProxyFactory proxyFactory = new ProxyFactory();

        proxyFactory.setTarget(new Target1());

        //切点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        pointcut.setExpression("execution(* foo())");

        //proxyFactory.addAdvice(new MethodInterceptor() {
        //    @Override
        //    public Object invoke(MethodInvocation invocation) throws Throwable {
        //        try {
        //            System.out.println("before ...");
        //            return invocation.proceed();
        //        } finally {
        //            System.out.println("after ...");
        //        }
        //
        //    }
        //});

        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(pointcut, new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                try {
                    System.out.println("before ...");
                    return invocation.proceed();
                } finally {
                    System.out.println("after ...");
                }

            }
        }));

        proxyFactory.setInterfaces(I1.class);
        proxyFactory.setProxyTargetClass(false);

        I1 proxy = (I1) proxyFactory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();
    }

    interface I1 {
        void foo();

        void bar();
    }

    static class Target1 implements I1 {


        @Override
        public void foo() {
            System.out.println("target1 foo()");
        }

        @Override
        public void bar() {
            System.out.println("target1 bar()");
        }
    }
}
