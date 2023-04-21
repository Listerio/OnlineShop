package com.example.onlineshopdemo.service;
import com.example.onlineshopdemo.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerService {
    public String addCustomer(Customer customer);
    public Optional<Customer> getCustomerById(Long customerId);
    public Optional<Customer> getCustomerByEmail(String email);
    public String updateCustomerPassword(Long customerId,String newPassword);
    public String  deleteCustomer(Long customerId);
    public List<Customer> getAll();
    void update(Customer customer);

}
