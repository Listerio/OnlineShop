package com.example.onlineshopdemo.controller;

import com.example.onlineshopdemo.entity.ProductOwner;
import com.example.onlineshopdemo.service.ProductOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/productOwners")
public class ProductOwnerController {
    private final ProductOwnerService productOwnerService;

    @Autowired
    public ProductOwnerController(ProductOwnerService productOwnerService) {
        this.productOwnerService = productOwnerService;
    }

    @PostMapping
    public String createProductOwner(@RequestBody ProductOwner productOwner) {
        return productOwnerService.createProductOwner(productOwner);
    }

    @GetMapping
    public String getAllProductOwners() {
        List<ProductOwner> productOwners = productOwnerService.getAllProductOwners();
        return productOwners.toString();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOwner> getProductOwnerById(@PathVariable("id") UUID productOwnerId) {
        ProductOwner productOwner = productOwnerService.getProductOwnerById(productOwnerId);
        if (productOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productOwner);
    }
    @GetMapping("/e/{email}")
    public String getProductOwnerByEmail(@PathVariable("email")String email){
        return productOwnerService.getProductOwnerByEmail(email).toString();
    }

    @DeleteMapping("/{id}")
    public String deleteProductOwnerById(@PathVariable("id") UUID productOwnerId) {
        return productOwnerService.deleteProductOwnerById(productOwnerId);
    }

}
