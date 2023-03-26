package com.example.onlineshopdemo.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customerCart;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cart_cart_id",joinColumns = @JoinColumn(name = "cart_id")
            ,inverseJoinColumns =@JoinColumn(name = "cart_item_id"))
    private List<CartItems> products;

    public Cart() {

    }
    public Cart(Customer customerCart) {
        this.customerCart = customerCart;
    }
    public Cart(Long id, Customer customerCart) {
        this.id = id;
        this.customerCart = customerCart;
    }
    public Cart(Long id, Customer customerCart, List<CartItems> products) {
        this.id = id;
        this.customerCart = customerCart;
        this.products = products;
    }
    public Customer getCustomerCart() {
        return customerCart;
    }
    public void setCustomerCart(Customer customerCart) {
        this.customerCart = customerCart;
    }
    public void addToCart(CartItems cartItems){
        products.add(cartItems);
    }
    public void removeFromCart(Long id){
        products.removeIf(p -> Objects.equals(p.getId(), id));
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<CartItems> getProducts() {
        return products;
    }
    public void setProducts(List<CartItems> products) {
        this.products = products;
    }
    public void clearCart(){
        products.clear();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customerCart=" + customerCart +
                ", products=" + products.toString() +
                '}'+"\n";
    }
}