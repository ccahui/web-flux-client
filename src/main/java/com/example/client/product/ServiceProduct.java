package com.example.client.product;

import com.example.client.product.dtos.Product;
import com.example.client.product.dtos.ProductCreateDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServiceProduct {
    Flux<Product> findAll();
    Mono<Product> findById(String id);
    Mono<Product> save(ProductCreateDto product);
    Mono<Void> deleteById(String id);
    Mono<Void> deleteAll();
}
