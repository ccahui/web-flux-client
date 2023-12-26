package com.example.client.api;

import com.example.client.api.config.ApiProductConfig;
import com.example.client.dtos.Product;
import com.example.client.dtos.ProductCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ApiProductService {

    private final ApiProductConfig apiProductConfig;
    public Flux<Product> findAll() {
        return null;
    }
    public Mono<Product> findById(String id) {
        return null;
    }
    public Mono<Product> save(ProductCreateDto product) {
        return null;
    }
    public Mono<Void> deleteById(String id) {
        return null;
    }
    public Mono<Void> deleteAll() {
        return null;
    }
}
