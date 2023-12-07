package com.example.springstudy.springboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;

public class TestAutoConfiguration {

    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.getDefaultListableBeanFactory().setAllowBeanDefinitionOverriding(false);
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(context.getBean(Bean1.class));
    }

    //@Configuration // 本项目的配置类
    @Import(MyImportSelector.class)
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1("本项目");
        }
    }

    static class MyImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//            for (String name : SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, null)) {
//                System.out.println(name);
//            }
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//            List<String> names = SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, null);
//            return names.toArray(new String[0]);
            return new String[]{AutoConfiguration1.class.getName(), AutoConfiguration2.class.getName()};
        }
    }

    @Configuration // 第三方的配置类
    static class AutoConfiguration1 {
        @Bean
        @ConditionalOnMissingBean
        public Bean1 bean1() {
            return new Bean1("第三方");
        }
    }

    static class Bean1 {
        private String name;

        public Bean1() {
        }

        public Bean1(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Bean1{" +
                   "name='" + name + '\'' +
                   '}';
        }
    }

    //@Configuration // 第三方的配置类
    static class AutoConfiguration2 {
        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean2 {

    }
}
