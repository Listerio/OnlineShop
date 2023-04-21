package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.enumerations.OrderStatus;


public interface OrderService {

    void createOrder(Order order);
    Order getOrder(Long id);
    String updateOrder(Long id, OrderStatus orderStatus);
    void deleteOrder(Long id);
    String getOrderByCustomer(Long id);

}
