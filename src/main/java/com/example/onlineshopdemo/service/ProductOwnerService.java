package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.ProductOwner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ProductOwnerService {
    ProductOwner createProductOwner(ProductOwner productOwner);

    List<ProductOwner> getAllProductOwners();

    ProductOwner getProductOwnerById(UUID productOwnerId);

    void deleteProductOwnerById(UUID productOwnerId);
    ProductOwner getProductOwnerByEmail(String email);
}
