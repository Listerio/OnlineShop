package com.example.onlineshopdemo.service;
import com.example.onlineshopdemo.entity.Cart;
import com.example.onlineshopdemo.entity.CartItems;

import java.util.List;


public interface CartItemsService {

    List<CartItems> clearCart(String email);
    String addToCart(Long productId,Integer number,String customerEmail);
    String updateCartItem(CartItems cartItems, String customerMail);
    void deleteCartItems(Long id);
    CartItems getCartItem(Long id);
    Cart getCart(String mail);



}
