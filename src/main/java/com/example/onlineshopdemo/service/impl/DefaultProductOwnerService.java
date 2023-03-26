package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.ProductOwnerRepository;
import com.example.onlineshopdemo.entity.ProductOwner;
import com.example.onlineshopdemo.service.ProductOwnerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultProductOwnerService implements ProductOwnerService {

    private final ProductOwnerRepository productOwnerRepository;

    @Autowired
    public DefaultProductOwnerService(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    @Override
    public ProductOwner createProductOwner(ProductOwner productOwner) {
        return productOwnerRepository.save(productOwner);
    }

    @Override
    public List<ProductOwner>    getAllProductOwners() {
        return productOwnerRepository.findAll();
    }

    @Override
    public ProductOwner getProductOwnerById(UUID productOwnerId) {
        Optional<ProductOwner> optionalProductOwner = productOwnerRepository.findById(productOwnerId);
        return optionalProductOwner.orElse(null);
    }

    @Override
    public void deleteProductOwnerById(UUID productOwnerId) {
        productOwnerRepository.deleteById(productOwnerId);
    }
    @Override
    public ProductOwner getProductOwnerByEmail(String email){
        return productOwnerRepository.findByEmail(email).orElse(null);
    }

}

