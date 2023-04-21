package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.OrderItemRepository;
import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.entity.OrderItem;
import com.example.onlineshopdemo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderItemService implements OrderItemService{

    @Autowired
    OrderItemRepository repository;


    @Override
    public void addOrderItem(OrderItem orderItem) {
        repository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItem(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void updateOrderItem(Long id, OrderItem orderItem) {
        orderItem.setId(id);
        repository.save(orderItem);
    }

    @Override
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

}
