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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItems> cartItems;

    public Cart() {

    }
    public Cart(Customer customerCart) {
        this.customerCart = customerCart;
    }
    public Cart(Long id, Customer customerCart) {
        this.id = id;
        this.customerCart = customerCart;
    }
    public Cart(Long id, Customer customerCart, List<CartItems> cartItems) {
        this.id = id;
        this.customerCart = customerCart;
        this.cartItems = cartItems;
    }
    public Customer getCustomerCart() {
        return customerCart;
    }
    public void setCustomerCart(Customer customerCart) {
        this.customerCart = customerCart;
    }
    public void addToCart(CartItems cartItems){
        this.cartItems.add(cartItems);
    }
    public void removeFromCart(Long id){
        cartItems.removeIf(p -> Objects.equals(p.getId(), id));
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<CartItems> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItems> cartItems) {
        this.cartItems = cartItems;
    }
    public void clearCart(){
        cartItems.clear();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customerCart=" + customerCart +
                ", products=" + cartItems.toString() +
                '}'+"\n";
    }
}