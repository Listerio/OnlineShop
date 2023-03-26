package com.example.onlineshopdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product_owner")
public class ProductOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @OneToMany(mappedBy = "productOwner",cascade = CascadeType.ALL)
    @JsonProperty("productLists")
    private List<Product> productList;

    public ProductOwner() {
    }

    public ProductOwner(UUID id,String name) {
        this.id = id;
        this.name = name;
    }

    public ProductOwner(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public ProductOwner(UUID id, String name, List<Product> productList) {
        this.id = id;
        this.name = name;
        this.productList = productList;
    }

    public void addToProductList(Product product){
        productList.add(product);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ProductOwner{" +
                "id=" + id +
                ",\n name='" + name + '\'' +
                ",\n email='" + email + '\'' +
                ",\n password='" + password + '\'' +
                ",\n productList=" + productList +
                "\n }";
    }
}
