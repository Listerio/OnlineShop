package com.example.onlineshopdemo.Restcontroller;

import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.service.CustomerService;
import com.example.onlineshopdemo.service.OrderService;
import com.example.onlineshopdemo.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

@Controller
@RequestMapping("/pay")
public class PaymentController {
    CustomerService customerService;
    PaymentService paymentService;

    @Autowired
    public PaymentController(CustomerService customerService, PaymentService paymentService) {
        this.customerService = customerService;
        this.paymentService = paymentService;
    }

    @GetMapping("/loadPage")
    public ModelAndView loadPage(HttpSession session){
        ModelAndView mav = new ModelAndView();
        Customer customer= (Customer) session.getAttribute("customer");
        Customer updatedCustomer = customerService.getCustomerByEmail(customer.getEmail()).orElse(null);
        if (updatedCustomer!=null) {
            List<CartItems> cartItemsList = updatedCustomer.getCart().getCartItems();
            double totalPrice = 0;
            for (CartItems cartItem : cartItemsList) {
                totalPrice += (cartItem.getProduct().getProductPrice()) * (cartItem.getNumber());
            }
            // Create a number formatter with the desired format
            NumberFormat formatter = new DecimalFormat("###,###,###.##");
            // Convert the number to a string with the desired format
            String formattedNumber = formatter.format(totalPrice);
            mav.addObject("totalPrice", formattedNumber);
            mav.addObject("customer",updatedCustomer);
            mav.setViewName("buyNow.html");
        }
        else {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @PostMapping("/paymentProcessing")
    public String processPayment(HttpSession session,
                                 @RequestParam("address") String address,
                                 @RequestParam("city") String city,
                                 @RequestParam("state") String  state,
                                 @RequestParam("cardNum") long accNum){
        String accNumber = accNum+"";
        if (accNumber.length()!=16){
            ModelAndView view=new ModelAndView();
            view.addObject("error","account number is meant to be 16 digits");
            session.setAttribute("errModel",view);
            System.out.println("not enough digits");
            return "redirect:/pay/loadPage";
        }
        else {
            if (session.getAttribute("errModel") !=null ){
                session.removeAttribute("errModel");
            }
            String[] details={city,state,address};
            Customer customer= (Customer) session.getAttribute("customer");
            paymentService.createPayment(customer.getEmail(),details);
        }
        return "redirect:/demo/shopping/indexPage/home";
    }









}
