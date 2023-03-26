package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.OrderRepository;
import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.enumerations.OrderStatus;
import com.example.onlineshopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

    OrderRepository repository;

    @Autowired
    public DefaultOrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createOrder(Order order) {
        repository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void updateOrder(Long id, OrderStatus orderStatus) {
        Order order=repository.findById(id).get();
        order.setStatus(orderStatus);
        repository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

}
