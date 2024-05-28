package com.example.kafka.autoconfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;

@Component
public class CustomKafkaDataSourceRegister implements InitializingBean {
    @Resource
    private DefaultListableBeanFactory beanFactory;

    @Resource
    private kafkaConsumerConfig kafkaConsumerConfig;

    @Override
    public void afterPropertiesSet() {
        Map<String, ConsumerConfigWrapper> factories = kafkaConsumerConfig.getFactories();
        if (factories != null && !factories.isEmpty()) {
            factories.forEach((factoryName, consumerConfig) -> {
                KafkaProperties.Listener listener = consumerConfig.getListener();
                Integer concurrency = consumerConfig.getConcurrency();
                // 创建监听容器工厂
                ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = createKafkaListenerContainerFactory(consumerConfig.buildProperties(), listener, concurrency);
                // 注册到容器
                if (!beanFactory.containsBean(factoryName)) {
                    beanFactory.registerSingleton(factoryName, containerFactory);
                }
            });
        }
    }

    /**
     * 创建containerFactory
     *
     * @param consumerProperties
     * @param listener
     * @return
     */
    private ConcurrentKafkaListenerContainerFactory<String, String> createKafkaListenerContainerFactory(Map<String, Object> consumerProperties, KafkaProperties.Listener listener, Integer concurrency) {
        ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProperties);
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        configureContainerFactory(factory, listener);
        if (!ObjectUtils.isEmpty(concurrency)) {
            factory.setConcurrency(concurrency);
        }
        return factory;
    }

    /**
     * 配置containerFactory
     *
     * @param containerFactory
     * @param listener
     */
    private void configureContainerFactory(ConcurrentKafkaListenerContainerFactory<String, String> containerFactory, KafkaProperties.Listener listener) {

        if (!ObjectUtils.isEmpty(listener)) {
            ContainerProperties container = containerFactory.getContainerProperties();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(listener::getAckMode).to(container::setAckMode);
            map.from(listener::getClientId).to(container::setClientId);
            map.from(listener::getAckCount).to(container::setAckCount);
            map.from(listener::getAckTime).as(Duration::toMillis).to(container::setAckTime);
            map.from(listener::getNoPollThreshold).to(container::setNoPollThreshold);
            map.from(listener::getIdleEventInterval).as(Duration::toMillis).to(container::setIdleEventInterval);
            map.from(listener::getPollTimeout).as(Duration::toMillis).to(container::setPollTimeout);
            map.from(listener::getMonitorInterval).as(Duration::getSeconds).as(Number::intValue).to(container::setMonitorInterval);
            map.from(listener::getLogContainerConfig).to(container::setLogContainerConfig);
            map.from(listener::isMissingTopicsFatal).to(container::setMissingTopicsFatal);
            if (listener.getType().equals(KafkaProperties.Listener.Type.BATCH)) {
                containerFactory.setBatchListener(true);
            }
        }
    }
}
