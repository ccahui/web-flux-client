package com.example.client.controller;


import com.example.client.dtos.Product;
import com.example.client.dtos.ProductCreateDto;
import com.example.client.service.ServiceProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api" + ProductController.PRODUCT)
@RequiredArgsConstructor
public class ProductController {
    public static final String PRODUCT = "/products";
    private final ServiceProduct service;
    @GetMapping
    public Flux<Product> all() {
        return service.findAll();
    }


    @GetMapping(value = {"/{id}"})
    public Mono<Product> show(@PathVariable String id) {
        return service.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@RequestBody ProductCreateDto product) {
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAll() {
        return service.deleteAll();
    }
}
