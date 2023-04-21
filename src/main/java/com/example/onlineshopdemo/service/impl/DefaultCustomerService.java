package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.CartRepository;
import com.example.onlineshopdemo.dao.CustomerRepository;
import com.example.onlineshopdemo.defaults.Defaults;
import com.example.onlineshopdemo.entity.Cart;
import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class DefaultCustomerService implements CustomerService {
    CustomerRepository customerRepository;
    CartRepository cartRepository;

    @Autowired
    public DefaultCustomerService(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public String addCustomer(Customer customer) {
        if (isEmailTaken(customer.getEmail())){
            Customer createdCustomer=customerRepository.save(customer);
            Cart cart=new Cart();
            cart.setCustomerCart(createdCustomer);
            cartRepository.save(cart);
            return Defaults.SUCCESS;
        }
        return Defaults.FAIL;
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public String updateCustomerPassword(Long customerId, String newPassword) {
        if (customerRepository.findById(customerId).isEmpty()){
            return Defaults.FAIL;
        }
        Customer customer=customerRepository.findById(customerId).get();
        customer.setPassword(newPassword);
        customerRepository.save(customer);
        return Defaults.SUCCESS;
    }

    @Override
    public String deleteCustomer(Long customerId) {
        if (customerRepository.findAll().stream().filter(customer -> Objects.equals(customer.getId(), customerId)
        ).findFirst().isEmpty()){
            return Defaults.FAIL;
        }
        customerRepository.deleteById(customerId);
        return Defaults.SUCCESS;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    private boolean isEmailTaken(String email) {
        return customerRepository.findAll().stream().filter(customer -> customer.getEmail().
                equals(email)).findFirst().isEmpty();
    }
    @Override
    public void update(Customer customer){
        Customer check=customerRepository.findById(customer.getId()).orElse(null);
        if (check==null){
            return;
        }
        customerRepository.save(customer);
    }

}
