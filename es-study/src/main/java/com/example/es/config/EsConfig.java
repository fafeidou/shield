package com.example.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix = "spring.data.elasticsearch.multiple")
@Configuration
@Data
public class EsConfig {
    private Map<String, ElasticsearchProperties> sources;
}
