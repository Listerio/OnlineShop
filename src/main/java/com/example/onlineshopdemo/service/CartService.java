package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Cart;


public interface CartService {

    Cart getCart(String mail);
    void updateCart(String mail ,Cart cart);
    String clearCart(String email);



}
