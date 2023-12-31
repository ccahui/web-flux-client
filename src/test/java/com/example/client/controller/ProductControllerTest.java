package com.example.client.controller;

import com.example.client.CustomTestConfig;
import com.example.client.api.ApiProductService;
import com.example.client.dtos.Product;
import com.example.client.utils.ConstantsUrl;
import com.example.client.utils.JsonConverterUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@CustomTestConfig
class ProductControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private JsonConverterUtil jsonConverterUtil;
    private String URL = ConstantsUrl.URL_BASE +ProductController.PRODUCT;
    private static WireMockServer serverProduct;
    @BeforeAll
    static void setUp() {
        // Configura WireMock manualmente
        serverProduct = new WireMockServer(WireMockConfiguration.options()
                .port(8090));
        serverProduct.start();
    }

    @AfterAll
    static void tearDown() {
        // Detiene WireMock después de todas las pruebas
        serverProduct.stop();
    }
    @Test
    void all() {
      serverProduct.stubFor(get(ApiProductService.API_PRODUCTS).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBodyFile("products/apiProductsResponseOk.json")));

        webTestClient.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(jsonConverterUtil.readContentFile("/api/client/products/productsResponseOk.json"))
                .jsonPath("$[0].name").isEqualTo("SONY CAMARA HD DIGITAL");
    }
}