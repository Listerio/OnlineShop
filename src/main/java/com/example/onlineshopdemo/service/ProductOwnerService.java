package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.ProductOwner;

import java.util.List;
import java.util.UUID;


public interface ProductOwnerService {
    String createProductOwner(ProductOwner productOwner);

    List<ProductOwner> getAllProductOwners();

    ProductOwner getProductOwnerById(UUID productOwnerId);

    String deleteProductOwnerById(UUID productOwnerId);
    ProductOwner getProductOwnerByEmail(String email);
}
