package com.example.springstudy.beanfactorypostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 通过BeanFactoryPostProcessor子接口BeanDefinitionRegistryPostProcessor
 * 1. 实现postProcessBeanDefinitionRegistry,之后进行注册registry.registerBeanDefinition("aTest2", beanDefinition)
 */
@SpringBootApplication
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestBeanFactoryPostProcessor.class, args);
        Test aTest = (Test) context.getBean("aTest2");
        System.out.println(aTest);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Test.class).getBeanDefinition();
        beanDefinition.setPropertyValues(getPropertyValues());
        registry.registerBeanDefinition("aTest2", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    private MutablePropertyValues getPropertyValues() {
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("description", "test_desc_2");
        return mutablePropertyValues;
    }

    public static class Test {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "description='" + description + '\'' +
                    '}';
        }
    }
}
