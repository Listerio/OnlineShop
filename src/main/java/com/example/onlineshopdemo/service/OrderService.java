package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.enumerations.OrderStatus;
import org.springframework.stereotype.Service;


public interface OrderService {

    void createOrder(Order order);
    Order getOrder(Long id);
    void updateOrder(Long id, OrderStatus orderStatus);
    void deleteOrder(Long id);

}
