package com.example.onlineshopdemo.dao;

import com.example.onlineshopdemo.entity.Cart;
import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByCustomerCart(Customer customer);

//    List<CartItems> findCartItemsByCartId(Long cartId);

}
