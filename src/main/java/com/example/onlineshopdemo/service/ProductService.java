package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Product;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface ProductService {

    void addProduct(Product product,String productOwnerEmail);
    void addProduct(Product product, UUID productOwnerId);
    Product getProduct(Long id);
    void updateProduct(Long id,Product product);
    void deleteProduct(Long id);

}
