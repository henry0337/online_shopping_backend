package com.henry.online_shopping.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private double price;
    private double rating;
    private List<Integer> size;
    private int categoryId;
    private int sellerId;
}
