package com.example.onlineshopdemo.service;
import com.example.onlineshopdemo.entity.Cart;
import com.example.onlineshopdemo.entity.CartItems;


public interface CartItemsService {

    void clearCart(String email);
    String addToCart(Long productId,Integer number,String customerEmail);
    void updateCartItem(int number, long cartItemId, String customerMail);
    void deleteCartItems(long cartItemId, String email);
    CartItems getCartItem(Long id);
    Cart getCart(String mail);



}
