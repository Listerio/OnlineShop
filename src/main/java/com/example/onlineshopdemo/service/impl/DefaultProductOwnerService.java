package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.ProductOwnerRepository;
import com.example.onlineshopdemo.dao.ProductRepository;
import com.example.onlineshopdemo.defaults.Defaults;
import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.entity.ProductOwner;
import com.example.onlineshopdemo.service.ProductOwnerService;

import java.util.List;

import com.example.onlineshopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultProductOwnerService implements ProductOwnerService {
    @Autowired
    private  ProductOwnerRepository productOwnerRepository;
    @Autowired
    private ProductRepository service;


    @Override
    public String createProductOwner(ProductOwner productOwner) {
        Optional<ProductOwner> productOwnerRet=getAllProductOwners().stream().
                filter(productOwner1 -> productOwner1.getEmail().equals(productOwner.getEmail())).findFirst();
        if (productOwnerRet.isEmpty()){
            ProductOwner productOwnerSaved= productOwnerRepository.save(productOwner);
            return Defaults.SUCCESS;
        }

        return Defaults.FAIL;
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
    public String deleteProductOwnerById(UUID productOwnerId) {
        ProductOwner productOwner=productOwnerRepository.findById(productOwnerId).orElse(null);
        if ( productOwner==null){
            return Defaults.FAIL;
        }
        List<Product> products=productOwner.getProductList();
        for (Product product:products) {
            service.deleteById(product.getId());
        }
        productOwnerRepository.deleteById(productOwnerId);
        return Defaults.SUCCESS;
    }
    @Override
    public ProductOwner getProductOwnerByEmail(String email){
        return productOwnerRepository.findByEmail(email).orElse(null);
    }

}

