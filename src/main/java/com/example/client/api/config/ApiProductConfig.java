package com.example.client.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "apis.product")
@Data
public class ApiProductConfig {

    private String apiUrl;
    private String apiKey;
}
