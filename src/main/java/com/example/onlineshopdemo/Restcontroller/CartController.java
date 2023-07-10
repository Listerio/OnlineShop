package com.example.onlineshopdemo.Restcontroller;

import com.example.onlineshopdemo.entity.CartItems;
import com.example.onlineshopdemo.entity.Customer;
import com.example.onlineshopdemo.entity.OrderItem;
import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.service.CartItemsService;
import com.example.onlineshopdemo.service.CustomerService;
import com.example.onlineshopdemo.service.PaymentService;
import com.example.onlineshopdemo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private CartItemsService cartItemsService;
    private CustomerService customerService;
    private ProductService productService;

    @Autowired
    public CartController( CartItemsService cartItemsService, CustomerService customerService, ProductService productService) {
        this.cartItemsService = cartItemsService;
        this.customerService = customerService;
        this.productService = productService;

    }

    @GetMapping("/myCart")
    public ModelAndView getCart(HttpSession session) {

        ModelAndView mav = new ModelAndView();
        Customer customer = (Customer) session.getAttribute("customer");
        Customer updatedCustomer = customerService.getCustomerByEmail(customer.getEmail()).orElse(null);
        if (updatedCustomer == null) {
            mav.setViewName("/");
            return mav;
        }
        List<CartItems> cartItemsList = updatedCustomer.getCart().getCartItems();
        mav.addObject("customer", updatedCustomer);
        mav.addObject("cartItems", cartItemsList);
        mav.setViewName("cart.html");
        session.setAttribute("customer", updatedCustomer);
        double totalPrice = 0;
        for (CartItems cartItem:cartItemsList) {
            totalPrice += (cartItem.getProduct().getProductPrice())*(cartItem.getNumber());
        }
        // Create a number formatter with the desired format
        NumberFormat formatter = new DecimalFormat("###,###,###.##");

        // Convert the number to a string with the desired format
        String formattedNumber = formatter.format(totalPrice);

        mav.addObject("totalPrice",formattedNumber);
        return mav;
    }

    @GetMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable("productId") int productId, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Product product = productService.getProduct((long) productId);
        cartItemsService.addToCart(product.getId(), 1, customer.getEmail());
        session.setAttribute("customer", customerService.getCustomerByEmail(customer.getEmail()).orElse(null));
        return "redirect:/demo/shopping/indexPage/home";
    }

    //
//    @GetMapping("/clear")
//    public ModelAndView removeDelete(){
//
//    }
    @GetMapping("/decrement/{cartItemId}")
    public String decUpdate(@PathVariable("cartItemId") int cartItemId, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        System.out.println("cartItem:  " + cartItemId);
        List<CartItems> cartItemsList = customer.getCart().getCartItems();
        CartItems cartItem = cartItemsService.getCartItem((long) cartItemId);
        if (cartItem.getNumber() == 1) {
            cartItemsService.deleteCartItems((long) cartItemId, customer.getEmail());
            return "redirect:/cart/myCart";
        }
        int currentNumber = cartItem.getNumber();
        cartItem.setNumber(currentNumber--);
        cartItemsService.updateCartItem(currentNumber, cartItemId, customer.getEmail());
        return "redirect:/cart/myCart";
    }

    @GetMapping("/increment/{cartItemId}")
    public String incUpdate(@PathVariable("cartItemId") int cartItemId, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        System.out.println("cartItem:  " + cartItemId);
        List<CartItems> cartItemsList = customer.getCart().getCartItems();
        CartItems cartItem = cartItemsService.getCartItem((long) cartItemId);
        int currentNumber = cartItem.getNumber();
        cartItem.setNumber(currentNumber++);
        cartItemsService.updateCartItem(currentNumber, cartItemId, customer.getEmail());

        return "redirect:/cart/myCart";
    }

    @GetMapping("/delete/{cartItemId}")
    public String delete(@PathVariable("cartItemId") int cartItemId, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        cartItemsService.deleteCartItems((long) cartItemId, customer.getEmail());
        return "redirect:/cart/myCart";
    }
}