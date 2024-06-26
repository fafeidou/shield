package com.example.es.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ElasticsearchProperties elasticsearchProperties;

    private RestHighLevelClient restHighLevelClient;

    @Bean(destroyMethod = "close")//这个close是调用RestHighLevelClient中的close
    @ConditionalOnMissingBean
    public RestHighLevelClient restHighLevelClient() {
        String host = elasticsearchProperties.getHost();
        String username = elasticsearchProperties.getUsername();
        String password = elasticsearchProperties.getPassword();
        Integer maxConnectTotal = elasticsearchProperties.getMaxConnectTotal();
        Integer maxConnectPerRoute = elasticsearchProperties.getMaxConnectPerRoute();
        Integer connectionRequestTimeoutMillis = elasticsearchProperties.getConnectionRequestTimeoutMillis();
        Integer socketTimeoutMillis = elasticsearchProperties.getSocketTimeoutMillis();
        Integer connectTimeoutMillis = elasticsearchProperties.getConnectTimeoutMillis();
        Long strategy = elasticsearchProperties.getKeepAliveStrategy();
        try {
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

            restHighLevelClient = new RestHighLevelClient(builder);
        } catch (Exception e) {
            logger.error("create RestHighLevelClient error", e);
            return null;
        }
        return restHighLevelClient;
    }
}
