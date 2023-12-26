package com.example.client.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private Double price;
    private Date createdAt;

    public Product(String name, Double price){
        this.name = name;
        this.price = price;
        this.createdAt = new Date();
    }
}
