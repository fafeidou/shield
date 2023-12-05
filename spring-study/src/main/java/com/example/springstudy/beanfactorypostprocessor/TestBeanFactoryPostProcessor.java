package com.example.springstudy.beanfactorypostprocessor;

import com.example.springstudy.utils.EnvironmentUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.IOException;

/**
 * 借助BeanFactoryPostProcessor，实现注册bean定义的几种方式
 *  1. beanFactory.registerBeanDefinition
 *  2. beanFactory.registerSingleton
 *  3. mutablePropertyValues.add("description", sources) 给bean添加属性
 *  4. String sources = environment.getProperty("customize.desc")
 *          从配置文件中读取配置(注意需要在spring.factories 中配置org.springframework.boot.env.EnvironmentPostProcessor )
 */
@SpringBootApplication
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentPostProcessor {
    private ConfigurableEnvironment environment;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(TestBeanFactoryPostProcessor.class, args);
        Test aTest = (Test) context.getBean("aTest");
        System.out.println(aTest);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //0. 设置环境信息
        EnvironmentUtil.setEnvironment(environment);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.environment = EnvironmentUtil.getEnvironment();
        MutablePropertyValues propertyValues = this.getPropertyValues();
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        //1. 注册Test.class
        this.registerBean(defaultListableBeanFactory, "aTest", Test.class, propertyValues);
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, String beanName, Class<?> beanClass,
                              MutablePropertyValues propertyValues) {
        AnnotatedBeanDefinition annotatedBeanDefinition = new AnnotatedGenericBeanDefinition(beanClass);
        ((AnnotatedGenericBeanDefinition) annotatedBeanDefinition).setPropertyValues(propertyValues);
        //3. 注册BeanDefinition
        beanFactory.registerBeanDefinition(beanName, annotatedBeanDefinition);

        //直接new对象，注册单例，
        //Test test = new Test();
        //test.setDescription(environment.getProperty("customize.desc"));
        //beanFactory.registerSingleton(beanName,test);
    }


    private MutablePropertyValues getPropertyValues() {
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        //2. 设置属性值
        //从配置文件中获取
        String sources = environment.getProperty("customize.desc");
        mutablePropertyValues.add("description", sources);
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
