package com.example.onlineshopdemo.service;
import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.entity.Customer;
import org.springframework.stereotype.Service;


public interface CartItemsService {

    public void addToCart(Long productId,Integer number,String customerEmail);
    void updateCartItem(CartItems cartItems,String customerMail);
    void deleteCartItems(Long id);
    CartItems getCartItem(Long id);



}
