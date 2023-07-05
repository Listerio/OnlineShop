package com.example.onlineshopdemo.Restcontroller;

import com.example.onlineshopdemo.defaults.Defaults;
import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.enumerations.UserRole;
import com.example.onlineshopdemo.service.CustomerService;
import com.example.onlineshopdemo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    CustomerService service;
    OrderService orderService;

    @Autowired
    public CustomerController(OrderService orderService, CustomerService service) {
        this.orderService = orderService;
        this.service = service;
    }
    @GetMapping("/customersList")
    public ModelAndView customerList(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("customerList",service.getAll());
        modelAndView.setViewName("customer-list");
        return modelAndView;
    }
    @GetMapping("/addUserForm")
    public ModelAndView showSignupForm() {
        ModelAndView view=new ModelAndView("register");
        Customer customer=new Customer();
        view.addObject("customer",customer);
        return view;
    }
    @GetMapping("/signInErrorHandler")
    public ModelAndView emailErrorHandler(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("emailError",true);
        Customer customer=new Customer();
        modelAndView.addObject("customer",customer);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/addUser")
    public ModelAndView submitSignupForm(@ModelAttribute Customer customer) {

        System.out.println("reached here");
        // Set the user role to "CUSTOMER"
        customer.setUserRole(UserRole.CUSTOMER);
        ModelAndView modelAndView = new ModelAndView();
        String check = service.addCustomer(customer);
        if (check.equals(Defaults.FAIL)) {
            modelAndView.setViewName("redirect:/customer/signInErrorHandler");
            return modelAndView;
        }
        // Set the view name here before returning the object
        modelAndView.setViewName("redirect:/customer/loginPage");
        return modelAndView;

    }

    @GetMapping("/loginPage")
    public ModelAndView login() {
        ModelAndView view=new ModelAndView("login");
        Customer customer=new Customer();
        view.addObject("signInCustomer",customer);
        return view;
    }
    @GetMapping("/login-fail")
    public ModelAndView signInFailure(HttpSession session){
        ModelAndView modelAndView= (ModelAndView) session.getAttribute("model");
        modelAndView.addObject("signInCustomer",new Customer());
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @PostMapping("/login-user")
    public String signIn(@ModelAttribute Customer customer, HttpSession session){
        Customer customer1=service.getCustomerByEmail(customer.getEmail()).orElse(null);
        System.out.println("email: "+customer.getEmail()+"password: "+customer.getPassword());
        ModelAndView modelAndView=new ModelAndView();
        if (customer1==null){
            modelAndView.addObject("error","email does not exist");
            session.setAttribute("model",modelAndView);
            return "redirect:/customer/login-fail";
        }
        else if (!customer1.getPassword().equals(customer.getPassword())){

            modelAndView.addObject("error","invalid password");
            session.setAttribute("model",modelAndView);
            return "redirect:/customer/login-fail";
        }
        session.setAttribute("customer",customer1);
        return "redirect:/demo/shopping/indexPage/home";
    }




}
