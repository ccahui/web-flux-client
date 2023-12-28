package com.example.client.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "apis.product")
@Data
public class ProductApiProperties {

    private String apiUrl;
    private String apiKey;
    private int connectTimeout = 3;
    private int socketTimeout = 10;

}
