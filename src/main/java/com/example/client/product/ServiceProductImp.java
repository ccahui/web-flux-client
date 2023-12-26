package com.example.client.product;

import com.example.client.exceptions.NotFoundException;
import com.example.client.product.api.ApiProductService;
import com.example.client.product.dtos.Product;
import com.example.client.product.dtos.ProductCreateDto;
import com.example.client.utils.CopyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class ServiceProductImp implements ServiceProduct {

    private final ApiProductService apiProductService;
    private final CopyProperties copyProperties;
    private static String notFoundMessage = "Product id ( %s )";
    @Override
    public Flux<Product> findAll() {
        return apiProductService.findAll();
    }

    @Override
    public Mono<Product> findById(String id) {
        return apiProductService.
                findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(notFoundMessage, id))));
    }

    @Override
    public Mono<Product> save(ProductCreateDto productDto) {
        return apiProductService.save(productDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return apiProductService.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return apiProductService.deleteAll();
    }
}
