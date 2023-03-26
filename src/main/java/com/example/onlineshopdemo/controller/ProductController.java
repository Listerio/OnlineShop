package com.example.onlineshopdemo.controller;

import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/adById/{id}")
    public void addProduct(@PathVariable("id") UUID productOwnerId ,@RequestBody Product product){
        productService.addProduct(product,productOwnerId);
    }
    @PostMapping("/adByEmail/{email}")
    public void addProduct(@PathVariable("email") String productEmail,@RequestBody Product product ){
        productService.addProduct(product,productEmail);
    }
    @GetMapping("{id}")
    public Product getProduct(@PathVariable("id") Long productId){
        return productService.getProduct(productId);
    }
    @PutMapping("{id}")
    public void updateProduct(@PathVariable("id") Long productId,@RequestBody Product product)
    {
        productService.updateProduct(productId,product);
    }
    @DeleteMapping("{id}")
    public void deleteProduct(@RequestParam("id") Long id){
        productService.deleteProduct(id);
    }








}
