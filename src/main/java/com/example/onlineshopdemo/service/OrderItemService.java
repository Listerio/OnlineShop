package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface OrderItemService{

    void addOrderItem(OrderItem orderItem);
    OrderItem getOrderItem(Long id);
    void updateOrderItem(Long id,OrderItem orderItem);
    void deleteOrder(Long id);

}

