package com.example.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
public class CustomEsDataSourceRegister implements InitializingBean {
    @Resource
    private DefaultListableBeanFactory beanFactory;

    @Resource
    private EsConfig esConfig;

    @Override
    public void afterPropertiesSet() {
        Map<String, ElasticsearchProperties> sources = esConfig.getSources();
        if (!ObjectUtils.isEmpty(sources)) {
            sources.forEach((sourceName, esConfig) -> registerES(sourceName, esConfig));
        }
    }

    private void registerES(String sourceName, ElasticsearchProperties elasticsearchProperties) {
        RestHighLevelClient restHighLevelClient = buildHighLevelClient(elasticsearchProperties);
        if (!ObjectUtils.isEmpty(restHighLevelClient)) {
            //beanFactory.registerSingleton(sourceName.concat("RestHighLevelClient"), restHighLevelClient);
            registerBeanWithConstructor(beanFactory, sourceName.concat("RestHighLevelClient"), RestHighLevelClient.class, producerFactoryValues(elasticsearchProperties));
        }
    }

    private static RestHighLevelClient buildHighLevelClient(ElasticsearchProperties elasticsearchProperties) {
        RestHighLevelClient restHighLevelClient = null;

        try {
            RestClientBuilder builder = getRestClientBuilder(elasticsearchProperties);
            restHighLevelClient = new RestHighLevelClient(builder);
        } catch (Exception e) {
            log.error("create RestHighLevelClient error", e);
        }
        return restHighLevelClient;
    }

    private ConstructorArgumentValues producerFactoryValues(ElasticsearchProperties elasticsearchProperties) {
        ConstructorArgumentValues mutablePropertyValues = new ConstructorArgumentValues();
        mutablePropertyValues.addIndexedArgumentValue(0, getRestClientBuilder(elasticsearchProperties));
        return mutablePropertyValues;
    }

    private static RestClientBuilder getRestClientBuilder(ElasticsearchProperties elasticsearchProperties) {
        String host = elasticsearchProperties.getHost();
        String username = elasticsearchProperties.getUsername();
        String password = elasticsearchProperties.getPassword();
        Integer maxConnectTotal = elasticsearchProperties.getMaxConnectTotal();
        Integer maxConnectPerRoute = elasticsearchProperties.getMaxConnectPerRoute();
        Integer connectionRequestTimeoutMillis = elasticsearchProperties.getConnectionRequestTimeoutMillis();
        Integer socketTimeoutMillis = elasticsearchProperties.getSocketTimeoutMillis();
        Integer connectTimeoutMillis = elasticsearchProperties.getConnectTimeoutMillis();
        Long strategy = elasticsearchProperties.getKeepAliveStrategy();
        String[] hosts = host.split(",");
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < httpHosts.length; i++) {
            httpHosts[i] = HttpHost.create(hosts[i]);
        }

        RestClientBuilder builder = RestClient.builder(httpHosts);
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeoutMillis);
            requestConfigBuilder.setSocketTimeout(socketTimeoutMillis);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeoutMillis);
            return requestConfigBuilder;
        });

        if (!StringUtils.isEmpty(username)) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));  //es账号密码（默认用户名为elastic）

            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.disableAuthCaching();
                httpClientBuilder.setMaxConnTotal(maxConnectTotal);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                if (strategy > 0) {
                    httpClientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> strategy);
                }
                return httpClientBuilder;
            });
        } else {
            builder.setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.disableAuthCaching();
                httpClientBuilder.setMaxConnTotal(maxConnectTotal);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                if (strategy > 0) {
                    httpClientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> strategy);
                }
                return httpClientBuilder;
            });
        }
        return builder;
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, String beanName, Class<?> beanClass,
                              MutablePropertyValues propertyValues) {
        AnnotatedBeanDefinition annotatedBeanDefinition = new AnnotatedGenericBeanDefinition(beanClass);
        ((AnnotatedGenericBeanDefinition) annotatedBeanDefinition).setPropertyValues(propertyValues);
        beanFactory.registerBeanDefinition(beanName, annotatedBeanDefinition);
    }

    private void registerBeanWithConstructor(DefaultListableBeanFactory beanFactory, String beanName, Class<?> beanClass,
                                             ConstructorArgumentValues propertyValues) {
        AnnotatedBeanDefinition annotatedBeanDefinition = new AnnotatedGenericBeanDefinition(beanClass);
        ((AnnotatedGenericBeanDefinition) annotatedBeanDefinition).setConstructorArgumentValues(propertyValues);
        beanFactory.registerBeanDefinition(beanName, annotatedBeanDefinition);
    }
}
