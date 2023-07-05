package com.example.onlineshopdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product_owner")
public class ProductOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;
    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @OneToMany(mappedBy = "productOwner",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonProperty("productLists")
    private List<Product> productList;

    public ProductOwner(UUID id, String firstName, String lastName, String email, String password, List<Product> productList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.productList = productList;
    }

    public ProductOwner() {
    }

    public ProductOwner(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}



