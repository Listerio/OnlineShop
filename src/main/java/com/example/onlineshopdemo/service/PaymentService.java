package com.example.onlineshopdemo.service;

import com.example.onlineshopdemo.entity.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    Payment createPayment(BigDecimal amount, LocalDate paymentDate, Long orderId);

    List<Payment> getAllPayments();

    Payment getPaymentById(Long paymentId);

    void deletePaymentById(Long paymentId);
}

