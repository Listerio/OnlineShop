package com.example.onlineshopdemo.controller;

import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.service.CartItemsService;
import com.example.onlineshopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    CustomerService customerService;
    CartItemsService cartItemsService;

    @Autowired
    public CartController(CustomerService customerService, CartItemsService cartItemsService) {
        this.customerService = customerService;
        this.cartItemsService = cartItemsService;
    }

    @PostMapping("{productId}/{number}/{customerEmail}")
    public String addToCart(@PathVariable("productId") Long productId,@PathVariable("number") Integer number,
                          @PathVariable("customerEmail") String customerEmail)
    {
       return cartItemsService.addToCart(productId,number,customerEmail);
    }

    @GetMapping("{email}")
    public String getCart(@PathVariable("email")String cartId){
        return cartItemsService.getCart(cartId).getProducts().toString();
    }
    @DeleteMapping("{email}")
    public void clearCart(@PathVariable("email") String email){
        cartItemsService.clearCart(email);
    }
    @PutMapping("{email}")
    public String updateItem(@RequestBody CartItems cartItems,@PathVariable("id") String email){
      return cartItemsService.updateCartItem(cartItems,email);
    }




}
