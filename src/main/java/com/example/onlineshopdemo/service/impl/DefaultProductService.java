package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.ProductRepository;
import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.entity.ProductOwner;
import com.example.onlineshopdemo.service.ProductOwnerService;
import com.example.onlineshopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductOwnerService productOwnerService;


    @Override
    public void addProduct(Product product, String productOwnerEmail) {
        ProductOwner productOwner=productOwnerService.getProductOwnerByEmail(productOwnerEmail);
        List<Product> products=productOwner.getProductList();
        product.setProductOwner(productOwner);
        for (Product pr:products ) {
            if (pr.equals(product)){
                return;
            }
        }
        productRepository.save(product);
    }

    @Override
    public void addProduct(Product product, UUID productOwnerId) {
        ProductOwner productOwner=productOwnerService.getProductOwnerById(productOwnerId);
        product.setProductOwner(productOwner);
        productRepository.save(product);
    }
    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    @Override
    public void updateProduct(Long id,Product product) {
        product.setId(id);
        productRepository.save(product);
    }
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }





}
