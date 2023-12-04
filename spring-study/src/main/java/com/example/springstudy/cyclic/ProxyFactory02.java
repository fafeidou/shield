package com.example.springstudy.cyclic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.support.GenericApplicationContext;

public class ProxyFactory02 {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1", Aspect1.class);
        context.registerBean("aspect2", Aspect2.class);
        context.registerBean( "aspect3", Aspect3.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class); //自动代理后处理器
        context.registerBean("target1",Target1.class);
        context.registerBean("target2",Target2.class);
        context.refresh();

        Target1 target1 = context.getBean(Target1.class);
        Target2 target2 = context.getBean(Target2.class);

        target1.foo();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        target2.bar();
    }

    static class Target1 {

        public void foo() {
            System.out.println("target1 foo()");
        }

    }

    static class Target2 {

        public void bar() {
            System.out.println("target2 bar()");
        }

    }

    @Aspect
    static class Aspect1 {
        @Around("execution(* foo())")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            System.out.println("aspect1 around");
            return pjp.proceed();
        }
    }

    @Aspect
    static class Aspect2 {
        @Before("execution(* foo())")
        public void before() {
            System.out.println("aspect2 before");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("aspect2 after");
        }

    }

    @Aspect
    static class Aspect3 {
        @Before("execution(* bar())")
        public void before() {
            System.out.println("aspect3 before");
        }

    }
}
