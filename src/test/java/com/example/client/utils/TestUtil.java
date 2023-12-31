package com.example.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

@Service
@Slf4j
public class TestUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();
    public  <T> T readFileToObject(String filePath, TypeReference<T> valueTypeRef) {
        T data = null;
        try {
            String content = readContentFile(filePath);
            data = objectMapper.readValue(content, valueTypeRef);
        }
        catch (JsonProcessingException errorJson) {
            log.error(errorJson.getMessage(),errorJson);
        }
        catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return data;
    }
    public  String readContentFile(String filePath) {
        String content = null;
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            File file = resource.getFile();
            content = new String(Files.readAllBytes(file.toPath()));
        }
        catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return content;
    }
    public WireMockServer wireMockServer(String urlWithPort) {
        try {
            URI uri = new URI(urlWithPort);
            int puerto = uri.getPort();
            return new WireMockServer(WireMockConfiguration.options().port(puerto));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URL invalid: " + urlWithPort, e);
        }
    }
}
