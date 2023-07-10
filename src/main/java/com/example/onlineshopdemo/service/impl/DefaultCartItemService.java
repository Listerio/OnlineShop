package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.CartItemRepository;
import com.example.onlineshopdemo.dao.CartRepository;
import com.example.onlineshopdemo.defaults.Defaults;
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
    public String addToCart(Long productId,Integer number,String customerEmail) {
        // Retrieve the customer by email
        Customer customer = customerService.getCustomerByEmail(customerEmail).orElse(null);
        if (customer==null){
            return Defaults.FAIL;
        }
        // Retrieve the cart for the customer
        Cart cart = customer.getCart();
        // Retrieve the product to add to the cart
        Product product = productService.getProduct(productId);
        if (product==null){
            return Defaults.FAIL;
        }
        // Create a new cart item with the product and quantity
        CartItems newCartItem = new CartItems(product,number);

        //Check if Product is already present
        long newProductId= newCartItem.getProduct().getId();
        List<CartItems> cartItemsList= cart.getCartItems();
        for (CartItems c:cartItemsList) {
            if (c.getProduct().getId()==newProductId){
                return Defaults.FAIL;
            }
        }
        // Add the cart item to the cart
        cart.addToCart(newCartItem);

        // Save the new cart item to the database
        cartRepository.save(cart);

        return Defaults.SUCCESS;
    }
    @Transactional
    @Override
    public void updateCartItem(int number, long cartItemsId, String customerMail) {
        Customer customer=customerService.getCustomerByEmail(customerMail).orElse(null);
        if (customer==null){
            return;
        }
        Cart cart=customer.getCart();
        List<CartItems> cartItemsList=cart.getCartItems();
        if (cartItemsList.isEmpty()){
            return;
        }
        for (CartItems c:cartItemsList) {
            if (c.getId()==cartItemsId){
                System.out.println("foundIt");
                c.setNumber(number);
                cart.setCartItems(cartItemsList);
                Cart update= cartRepository.save(cart);
            }
        }
    }

    @Override
    @Transactional
    public void deleteCartItems(long cartItemId, String email) {
        Customer customer= customerService.getCustomerByEmail(email).orElse(null);
        if (customer!=null){
            Cart c= customer.getCart();
            List<CartItems> cartItemsList = c.getCartItems();
            CartItems cartItem = cartItemsList.stream().filter(cartItems -> cartItems.getId() == cartItemId).findFirst().orElse(null);
            cartItemsList.remove(cartItem);
            cartRepository.save(c);
            cartItemRepository.deleteById(cartItemId);
        }
    }

    @Override
    public CartItems getCartItem(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }


    @Override
    public Cart getCart(String mail) {
        return customerService.getCustomerByEmail(mail).get().getCart();
    }

    @Override
    @Transactional
    public void clearCart(String email) {
        Customer customer = customerService.getCustomerByEmail(email).orElse(null);
        if (customer!=null) {
            Cart cart = customer.getCart();
            List<CartItems> cartItemsList=cart.getCartItems();
            cart.clearCart();
            cartItemRepository.deleteAll(cartItemsList);
            cartRepository.save(cart);
        }
    }

    private void deleteAll(List<CartItems> cartItemsList){cartItemRepository.deleteAll(cartItemsList);}

}
