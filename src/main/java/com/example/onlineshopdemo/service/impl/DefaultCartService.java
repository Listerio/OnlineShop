package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.CartItemRepository;
import com.example.onlineshopdemo.dao.CartRepository;
import com.example.onlineshopdemo.defaults.Defaults;
import com.example.onlineshopdemo.entity.Cart;
import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.service.CartItemsService;
import com.example.onlineshopdemo.service.CartService;
import com.example.onlineshopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DefaultCartService implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerService service;
    @Autowired
    CartItemRepository cartItemRepository;


    @Override
    public Cart getCart(String mail) {
        return service.getCustomerByEmail(mail).get().getCart();
    }

    @Override
    public void updateCart(String mail, Cart cart) {
        Customer customer=service.getCustomerByEmail(mail).get();
        Cart cartCustomer=customer.getCart();
        cart.setId(cartCustomer.getId());
        cartRepository.save(cart);
    }

    @Override
    public String clearCart(String email) {
        Customer customer=service.getCustomerByEmail(email).orElse(null);
        Cart cart=customer.getCart();
        for (CartItems c:cart.getProducts()) {
            CartItems cartItems = cartItemRepository.findById(c.getId()).orElse(null);
            cartItemRepository.deleteById(cartItems.getId());
        }
        return Defaults.SUCCESS;
    }



}
