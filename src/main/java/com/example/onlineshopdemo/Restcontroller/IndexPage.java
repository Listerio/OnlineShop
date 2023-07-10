package com.example.onlineshopdemo.Restcontroller;

import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.enumerations.Category;
import com.example.onlineshopdemo.enumerations.Subcategory;
import com.example.onlineshopdemo.service.CustomerService;
import com.example.onlineshopdemo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/demo/shopping/indexPage")
public class IndexPage {

    @Autowired
    ProductService productService;

    @GetMapping("/home")
    public ModelAndView goToHomepage(HttpSession session){
        Customer customer=(Customer) session.getAttribute("customer");
        ModelAndView modelAndView=new ModelAndView("index.html");
        List<Product> productList=productService.products();
        modelAndView.addObject("productsList",productList);
        if (customer==null){
            //force them to create account or login before they can proceed etc.
            System.out.println("here");
            modelAndView.setViewName("redirect:/customer/loginPage");
            return modelAndView;
        }
        //after logging in
        //just checking the console.
        modelAndView.addObject("customer",customer);
        List<Product> products=productService.products();
        modelAndView.addObject("all_products", products);
        return modelAndView;
    }




}
