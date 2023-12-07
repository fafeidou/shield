package com.example.springstudy.springboot;

import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * 条件装配的底层是本质上是 @Conditional 与 Condition，这两个注解。引入自动配置类时，
 * 期望满足一定条件才能被 Spring 管理，不满足则不管理，怎么做呢？
 * 比如条件是【类路径下必须有 dataSource】这个 bean ，怎么做呢？
 */
public class TestConditionAutoConfiguration {

    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

    @Configuration // 本项目的配置类
    @Import(MyImportSelector.class)
    static class Config {
    }

    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{AutoConfiguration1.class.getName(), AutoConfiguration2.class.getName()};
        }
    }

    static class MyCondition implements Condition { // 存在 Druid 依赖
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());
            String className = attributes.get("className").toString();
            boolean exists = (boolean) attributes.get("exists");
            boolean present = ClassUtils.isPresent(className, null);
            return exists ? present : !present;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Conditional(MyCondition.class)
    @interface ConditionalOnClass {
        boolean exists(); // true 判断存在 false 判断不存在

        String className(); // 要判断的类名
    }

    @Configuration // 第三方的配置类
    @ConditionalOnClass(className = "com.alibaba.druid.pool.DruidDataSource", exists = false)
    static class AutoConfiguration1 {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }
    }

    @Configuration // 第三方的配置类
    @ConditionalOnClass(className = "com.alibaba.druid.pool.DruidDataSource", exists = true)
    static class AutoConfiguration2 {
        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {

    }

    static class Bean2 {

    }
}
