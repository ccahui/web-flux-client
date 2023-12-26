package com.example.client.product.api;

import com.example.client.product.dtos.Product;
import com.example.client.product.dtos.ProductCreateDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ApiProductService {


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
