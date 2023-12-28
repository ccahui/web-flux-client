package com.example.client.api;


import com.example.client.dtos.ApiBadRequestResponse;
import com.example.client.dtos.Product;
import com.example.client.dtos.ProductCreateDto;
import com.example.client.exceptions.BadRequestException;
import com.example.client.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ApiProductService {
    private static String notFoundMessage = "Product id ( %s )";
    private final WebClient productWebClient;
    public Flux<Product> findAll() {
        return productWebClient
                .get().uri("/api/products").retrieve()
                .bodyToFlux(Product.class);
    }
    public Mono<Product> findById(String id) {
        String uri = String.format("/api/products/%s", id);
        return productWebClient
                .get().uri(uri)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.equals(HttpStatus.NOT_FOUND),
                        response -> {
                            throw new NotFoundException(String.format(notFoundMessage, id));
                        })
                .bodyToMono(Product.class);
    }
    public Mono<Product> save(ProductCreateDto product) {
        String uri = "/api/products";
        return productWebClient
                .post().uri(uri)
                .body(Mono.just(product), ProductCreateDto.class)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.equals(HttpStatus.BAD_REQUEST),
                        response -> {

                            return response.bodyToMono(ApiBadRequestResponse.class)
                                    .flatMap(errorResponse->{
                                        throw new BadRequestException(errorResponse);
                                    });
                        })
                .bodyToMono(Product.class);
    }
    public Mono<Void> deleteById(String id) {
        String uri = String.format("/api/products/%s", id);
        return productWebClient
                .delete().uri(uri)
                .retrieve()
                .bodyToMono(Void.class);
    }
    public Mono<Void> deleteAll() {
        String uri = "/api/products/all";
        return productWebClient
                .delete().uri(uri)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
