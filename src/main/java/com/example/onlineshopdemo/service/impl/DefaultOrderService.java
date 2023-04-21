package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.OrderRepository;
import com.example.onlineshopdemo.defaults.Defaults;
import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.enumerations.OrderStatus;
import com.example.onlineshopdemo.service.CustomerService;
import com.example.onlineshopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultOrderService implements OrderService {

    OrderRepository repository;
    CustomerService service;

    @Autowired
    public DefaultOrderService(OrderRepository repository, CustomerService service) {
        this.repository = repository;
        this.service = service;
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
    public String updateOrder(Long id, OrderStatus orderStatus) {
        Order order=repository.findById(id).orElse(null);
        if (order==null){
            return Defaults.FAIL;
        }
        order.setStatus(orderStatus);
        repository.save(order);
        return Defaults.SUCCESS;
    }

    @Override
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

    @Override
    public String getOrderByCustomer(Long id) {

        List<Order> order= repository.findByCustomerId(id);
        System.out.println(order.toString());
        return order.toString();
    }

}
