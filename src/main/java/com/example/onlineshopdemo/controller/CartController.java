package com.example.onlineshopdemo.controller;

import com.example.onlineshopdemo.entity.Cart;
import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.service.CartItemsService;
import com.example.onlineshopdemo.service.CartService;
import com.example.onlineshopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    CustomerService customerService;
    CartItemsService cartItemsService;
    CartService cartService;

    @Autowired
    public CartController(CustomerService customerService, CartItemsService cartItemsService, CartService cartService) {
        this.customerService = customerService;
        this.cartItemsService = cartItemsService;
        this.cartService = cartService;
    }

    @PostMapping("{productId}/{number}/{customerEmail}")
    public void addToCart(@PathVariable("productId") Long productId,@PathVariable("number") Integer number,
                          @PathVariable("customerEmail") String customerEmail)
    {
        cartItemsService.addToCart(productId,number,customerEmail);
    }

    @GetMapping("{email}")
    public String getCart(@PathVariable("email")String cartid){
        return cartService.getCart(cartid).getProducts().toString();
    }
    @DeleteMapping("{email}")
    public void clearCart(@PathVariable("email") String cartId){
        cartService.clearCart(cartId);
    }
    @PutMapping("{email}")
    public void updateItem(@RequestBody CartItems cartItems,@PathVariable("id") String email){
        cartItemsService.updateCartItem(cartItems,email);
    }



}
