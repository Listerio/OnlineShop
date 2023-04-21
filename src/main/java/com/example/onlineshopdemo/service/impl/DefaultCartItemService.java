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
        CartItems newCartItem = new CartItems(product,number,customer);

        // Add the cart item to the cart
        cart.addToCart(newCartItem);

        // Save the new cart item to the database
        cartItemRepository.save(newCartItem);

        // Save the cart to the database
        cartRepository.save(cart);
        return Defaults.SUCCESS;
    }

    @Override
    public String updateCartItem(CartItems cartItems, String customerMail) {
        Customer customer=customerService.getCustomerByEmail(customerMail).orElse(null);
        if (customer==null){
            return Defaults.FAIL;
        }
        Cart cart=customer.getCart();
        List<CartItems> cartItemsList=cart.getProducts();
        if (cartItemsList.isEmpty()){
            return Defaults.FAIL;
        }
        CartItems cartItemFromList=cartItemsList.stream().filter(cartItems1 ->
                        cartItems1.getId().equals(cartItems.getId())).findFirst().get();
        cartItemsList.remove(cartItemFromList);
        cartItemsList.add(cartItems);
        cart.setProducts(cartItemsList);
        cartRepository.save(cart);
        return Defaults.SUCCESS;
    }

    @Override
    public void deleteCartItems(Long id) {
        cartItemRepository.deleteById(id);
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
    public List<CartItems> clearCart(String email) {
        Customer customer = customerService.getCustomerByEmail(email).orElse(null);
        if (customer!=null) {
            Cart cart = cartRepository.findByCustomerCart(customer);
            System.out.println("cartItems: "+cart.getProducts().toString());
            cart.clearCart();
            List<CartItems> cartItemsList=cartItemRepository.findAllByCustomerId(customer.getId());
            System.out.print("Cart item:  ");
            cartItemsList.stream().forEach(System.out::println);
            deleteAll(cartItemsList);
            cartRepository.save(cart);
            return cart.getProducts();
        }
        return null;
    }

    private void deleteAll(List<CartItems> cartItemsList){cartItemRepository.deleteAll(cartItemsList);}

}
