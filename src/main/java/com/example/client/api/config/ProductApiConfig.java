package com.example.client.api.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class ProductApiConfig {

    private final ProductApiProperties productApiProperties;

    @Bean
    public WebClient productWebClient() {
        return WebClient.builder()
                .baseUrl(productApiProperties.getApiUrl())
                .defaultHeader("api-key", productApiProperties.getApiKey())
                .clientConnector(createReactorClientHttpConnector())
                .build();
    }
    private ClientHttpConnector createReactorClientHttpConnector() {
        return new ReactorClientHttpConnector(HttpClient.create()
                .responseTimeout(Duration.ofSeconds(productApiProperties.getSocketTimeout()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(productApiProperties.getSocketTimeout()))
                ));
    }
}
