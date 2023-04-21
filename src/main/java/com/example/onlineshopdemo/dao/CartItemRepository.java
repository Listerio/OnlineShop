package com.example.onlineshopdemo.dao;

import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
//    void deleteAllByCustomerId(Long customerId);
    List<CartItems> findAllByCustomerId(Long customerId);
}


