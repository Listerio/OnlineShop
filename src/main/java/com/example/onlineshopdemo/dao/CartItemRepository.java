package com.example.onlineshopdemo.dao;

import com.example.onlineshopdemo.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {

}
