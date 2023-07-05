package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    void addProduct(Product product,String productOwnerEmail);
    Product addProduct(Product product, UUID productOwnerId);
    Product getProduct(Long id);
    void updateProduct(Long id,Product product);
    void deleteProduct(UUID productOwnerId,Long id);
    List<Product> products();

}
