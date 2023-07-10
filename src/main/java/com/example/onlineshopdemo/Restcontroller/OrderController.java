package com.example.onlineshopdemo.Restcontroller;

import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.entity.Order;
import com.example.onlineshopdemo.entity.OrderItem;
import com.example.onlineshopdemo.entity.Payment;
import com.example.onlineshopdemo.service.CustomerService;
import com.example.onlineshopdemo.service.OrderService;
import com.example.onlineshopdemo.service.PaymentService;
import com.example.onlineshopdemo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    CustomerService customerService;
    OrderService orderService;
    PaymentService paymentService;

    @Autowired
    public OrderController(PaymentService paymentService,CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.paymentService=paymentService;
    }

    @GetMapping("/getOrder")
    public ModelAndView displayOrders(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        ModelAndView modelAndView = new ModelAndView("orders.html");
        Customer updatedCustomer = customerService.getCustomerByEmail(customer.getEmail()).orElse(null);
        if (updatedCustomer != null) {
            List<Order> orders = updatedCustomer.getOrders();
            modelAndView.addObject("orders", orders);

            List<Payment> payments = paymentService.getPaymentsPerCustomer(customer.getEmail());
            modelAndView.addObject("payments", payments);

            modelAndView.addObject("orderItemsMap", getOrderItemsMap(orders));
            modelAndView.addObject("totalSpent", calculateTotalSpent(payments));
        }
        return modelAndView;
    }

    private Map<Long, List<OrderItem>> getOrderItemsMap(List<Order> orders) {
        Map<Long, List<OrderItem>> orderItemsMap = new HashMap<>();
        for (Order order : orders) {
            orderItemsMap.put(order.getId(), order.getOrderItems());
        }
        return orderItemsMap;
    }

    private BigDecimal calculateTotalSpent(List<Payment> payments) {
        BigDecimal totalSpent = BigDecimal.ZERO;
        for (Payment payment : payments) {
            totalSpent = totalSpent.add(payment.getAmount());
        }
        return totalSpent;
    }

}
