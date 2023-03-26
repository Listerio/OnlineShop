package com.example.onlineshopdemo.service.impl;



import com.example.onlineshopdemo.dao.PaymentRepository;
import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.entity.Payment;
import com.example.onlineshopdemo.service.OrderService;
import com.example.onlineshopdemo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultpaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Autowired
    public DefaultpaymentService(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Override
    public Payment createPayment(BigDecimal amount, LocalDate paymentDate, Long orderId) {
        Order order=orderService.getOrder(orderId);
        Payment payment = new Payment(amount, paymentDate, order);
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        return paymentOptional.orElse(null);
    }

    @Override
    public void deletePaymentById(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
