package com.example.onlineshopdemo.service.impl;

import com.example.onlineshopdemo.dao.PaymentRepository;
import com.example.onlineshopdemo.defaults.Defaults;
import com.example.onlineshopdemo.entity.*;
import com.example.onlineshopdemo.enumerations.OrderStatus;
import com.example.onlineshopdemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultpaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final CartItemsService cartItemsService;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;
    @Autowired
    public DefaultpaymentService(CustomerService customerService, PaymentRepository paymentRepository, OrderService orderService, CartItemsService cartItemsService, OrderItemService orderItemService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.cartItemsService = cartItemsService;
        this.customerService=customerService;
        this.orderItemService = orderItemService;
    }

    @Override
    public String createPayment(String email) {
        Customer customer=customerService.getCustomerByEmail(email).orElse(null);
        if(customer==null){
            return Defaults.FAIL;
        }
        double totalPrice=0;
        LocalDate localDate=LocalDate.now();
        Cart cart=customer.getCart();
        List<CartItems> cartItems=cart.getProducts();

        if(cart.getProducts().isEmpty()){
            return Defaults.FAIL;
        }

        Order order=new Order();
        order.setCustomer(customer);
        order.setOrderDate(localDate);
        order.setStatus(OrderStatus.CREATED);
        orderService.createOrder(order);

        for (CartItems cartItem:cartItems) {
            totalPrice+=(cartItem.getProduct().getProductPrice())*(cartItem.getNumber());
            OrderItem item=new OrderItem();
            item.setOrderedProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getNumber());
            item.setOrder(order);
            orderItemService.addOrderItem(item);
        }

        Payment payment=new Payment();
        BigDecimal price=BigDecimal.valueOf(totalPrice);
        payment.setAmount(price);
        payment.setPaymentDate(localDate);
        payment.setOrder(order);
        paymentRepository.save(payment);
        cartItemsService.clearCart(customer.getEmail());
        List<Payment> payments=customer.getPayments();
        payments.add(payment);
        customer.setPayments(payments);
        customerService.update(customer);

        //Send mail
        return Defaults.SUCCESS;
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
    public List<Payment> getPaymentsPerCustomer(String email){
        Customer customer=customerService.getCustomerByEmail(email).orElse(null);
        return customer.getPayments();
    }

}
