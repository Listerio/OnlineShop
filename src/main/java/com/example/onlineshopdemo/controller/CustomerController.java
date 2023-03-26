package com.example.onlineshopdemo.controller;

import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.enumerations.UserRole;
import com.example.onlineshopdemo.service.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }
    @PostMapping
    public String createCustomer(@RequestBody Customer customer){
        customer.setUserRole(UserRole.CUSTOMER);
        return service.addCustomer(customer);
    }

    @GetMapping("{id}")
    public String getCustomer(@PathVariable("id")Long id){
        return service.getCustomerById(id).orElse(null).toString();
    }
    @PutMapping("{id}")
    public String updatePassword(@PathVariable Long id,@RequestBody String password){
        return service.updateCustomerPassword(id, password);
    }

    @DeleteMapping("{id}")
    public String deleteCustomer(@PathVariable("id")Long id)
    {
        return service.deleteCustomer(id);
    }



}
