package com.example.kafka;

import com.example.kafka.autoconfig.CustomKafkaDataSourceRegister;
import com.example.kafka.autoconfig.kafkaConsumerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnWebApplication
@EnableConfigurationProperties({kafkaConsumerConfig.class})
@Import({CustomKafkaDataSourceRegister.class})
public class MyKafkaAutoConfiguration implements BeanFactoryAware, SmartInstantiationAwareBeanPostProcessor {
    public MyKafkaAutoConfiguration() {
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        beanFactory.getBean(CustomKafkaDataSourceRegister.class);
    }
}
