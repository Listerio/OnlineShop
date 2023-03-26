package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.CartItemRepository;
import com.example.onlineshopdemo.dao.CartRepository;
import com.example.onlineshopdemo.entity.Cart;
import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.service.CartItemsService;
import com.example.onlineshopdemo.service.CustomerService;
import com.example.onlineshopdemo.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCartItemService implements CartItemsService {
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;


    @Transactional
    @Override
    public void addToCart(Long productId,Integer number,String customerEmail) {
        // Retrieve the customer by email
        Customer customer = customerService.getCustomerByEmail(customerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Retrieve the cart for the customer
        Cart cart = customer.getCart();
        // Retrieve the product to add to the cart
        Product product = productService.getProduct(productId);

        // Create a new cart item with the product and quantity
        CartItems newCartItem = new CartItems(product,number);

        // Add the cart item to the cart
        cart.addToCart(newCartItem);

        // Save the new cart item to the database
        cartItemRepository.save(newCartItem);

        // Save the cart to the database
        cartRepository.save(cart);
    }

    @Override
    public void updateCartItem(CartItems cartItems, String customerMail) {
        Customer customer=customerService.getCustomerByEmail(customerMail).get();
        Cart cart=customer.getCart();
        List<CartItems> cartItemsList=cart.getProducts();
        CartItems cartItemFromList=cartItemsList.stream().filter(cartItems1 -> cartItems1.getId().equals(cartItems.getId()))
                .findFirst().get();
        cartItemsList.remove(cartItemFromList);
        cartItemsList.add(cartItems);
        cart.setProducts(cartItemsList);
        cartRepository.save(cart);
    }

    @Override
    public void deleteCartItems(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItems getCartItem(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }
}
