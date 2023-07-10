package com.example.onlineshopdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @ManyToOne
    @JsonProperty("product")
    private Product product;
    @JsonProperty("number")
    private int number;


    public CartItems(Long id, Product product, int number ) {
        this.id = id;
        this.product = product;
        this.number = number;


    }
    public CartItems(){

    }
    public CartItems(Product product, int number) {
        this.product = product;
        this.number = number;

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    @Override
    public String toString() {
        return "CartItems{" +
                "id=" + id +
                ", product=" + product.getProductName() +
                ", number=" + number +
                '}';
    }
}
