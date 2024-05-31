package com.example.es.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnWebApplication
@EnableConfigurationProperties({EsConfig.class})
@Import({CustomEsDataSourceRegister.class})
public class MyEsAutoConfiguration implements BeanFactoryAware, SmartInstantiationAwareBeanPostProcessor {
    public MyEsAutoConfiguration() {
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        beanFactory.getBean(CustomEsDataSourceRegister.class);
    }
}
