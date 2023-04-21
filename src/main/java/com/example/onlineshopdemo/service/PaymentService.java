package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Payment;

import java.util.List;

public interface PaymentService {
    String createPayment(String email);
    List<Payment> getAllPayments();
    public List<Payment> getPaymentsPerCustomer(String email);
    Payment getPaymentById(Long paymentId);

}

