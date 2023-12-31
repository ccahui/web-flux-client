package com.example.client.controller;

import com.example.client.CustomTestConfig;
import com.example.client.api.ApiProductService;
import com.example.client.api.config.ProductApiProperties;
import com.example.client.dtos.ProductCreateDto;
import com.example.client.utils.ConstantsUrl;
import com.example.client.utils.TestUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CustomTestConfig
@Slf4j
class ProductControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private TestUtil testUtil;
    private String URL = ConstantsUrl.URL_BASE +ProductController.PRODUCT;
    private WireMockServer serverProduct;

    @Autowired
    private ProductApiProperties productApiProperties;
    @BeforeEach
    void setUp() {
        // Configura servidor Mock de Productos
        serverProduct = testUtil.wireMockServer(productApiProperties.getApiUrl());
        serverProduct.start();
    }

    @AfterEach
    void tearDown() {
        // Detiene WireMock después
        serverProduct.stop();
    }
    @Test
    void all() {
      serverProduct.stubFor(get(ApiProductService.API_PRODUCTS).willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withBodyFile("products/apiProductsResponseOk.json")));

        webTestClient.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(testUtil.readContentFile("/api/client/products/productsResponseOk.json"))
                .jsonPath("$[0].name").isEqualTo("SONY CAMARA HD DIGITAL");
    }
    @Test
    void findByIdSuccess() {
        String id = "658cad206513575c72a83228";
        String apiUrl =ApiProductService.API_PRODUCTS+"/"+id;
        String url = URL + "/"+id;
        serverProduct.stubFor(get(apiUrl).willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withBodyFile("products/apiProductResponseOk.json")));

        webTestClient.get()
                .uri(url)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(testUtil.readContentFile("/api/client/products/productResponseOk.json"))
                .jsonPath("$.name").isEqualTo("TV PANASONIC PANTALLA LCD");
    }
    @Test
    void findByIdError404() {
        String id = "658cad206513575c72a83228a";
        String apiUrl =ApiProductService.API_PRODUCTS+"/"+id;
        String url = URL + "/"+id;

        serverProduct.stubFor(get(apiUrl).willReturn(aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withHeader("Content-Type", "application/json")
                .withBodyFile("products/apiProductResponse404.json")));

        webTestClient.get()
                .uri(url)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json(testUtil.readContentFile("/api/client/products/productResponse404.json"))
                .jsonPath("$.message").isEqualTo("Not Found Exception (404). Product id ( 658cad206513575c72a83228a )");
    }
    @Test
    void deleteById() {
        String id = "658cad206513575c72a83228a";
        String apiUrl =ApiProductService.API_PRODUCTS+"/"+id;
        String url = URL + "/"+id;

        serverProduct.stubFor(delete(apiUrl).willReturn(aResponse()
                .withStatus(HttpStatus.NO_CONTENT.value())
                .withHeader("Content-Type", "application/json")
                .withBody("")));

        webTestClient.delete()
                .uri(url)
                .exchange()
                .expectStatus().isNoContent();
    }
    @Test
    void deleteAll() {
        String apiUrl =ApiProductService.API_PRODUCTS+"/all";
        String url = URL + "/all";
        serverProduct.stubFor(delete(apiUrl).willReturn(aResponse()
                .withStatus(HttpStatus.NO_CONTENT.value())
                .withHeader("Content-Type", "application/json")
                .withBody("")));

        webTestClient.delete()
                .uri(url)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void create() {
        serverProduct.stubFor(post(ApiProductService.API_PRODUCTS).willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json")
                .withBodyFile("products/apiCreateProductResponseOk.json")));

        webTestClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testUtil.readContentFile(URL+"/createProductRequestOk.json"))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json(testUtil.readContentFile("/api/client/products/createProductResponseOk.json"))
                .jsonPath("$.id").isEqualTo("658cb1956513575c72a83233");

        serverProduct.verify(postRequestedFor(urlEqualTo(ApiProductService.API_PRODUCTS))
                .withRequestBody(equalToJson(testUtil.readContentFile("/__files/products/apiCreateProductRequestOk.json"))));
    }
    @Test
    void createBadRequest400() {
        serverProduct.stubFor(post(ApiProductService.API_PRODUCTS).willReturn(aResponse()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withHeader("Content-Type", "application/json")
                .withBodyFile("products/apiCreateProductResponseNameEmpty400.json")));

        webTestClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testUtil.readContentFile(URL+"/createProductRequestNameEmpty400.json"))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .json(testUtil.readContentFile("/api/client/products/createProductResponseNameEmpty400.json"))
                .jsonPath("$.exception").isEqualTo("BadRequestException");

        serverProduct.verify(postRequestedFor(urlEqualTo(ApiProductService.API_PRODUCTS))
                .withRequestBody(equalToJson(testUtil.readContentFile("/__files/products/apiCreateProductRequestNameEmpty400.json"))));
    }
}