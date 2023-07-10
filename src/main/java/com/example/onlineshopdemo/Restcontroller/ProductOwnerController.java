package com.example.onlineshopdemo.Restcontroller;

import com.example.onlineshopdemo.defaults.Defaults;
import com.example.onlineshopdemo.entity.Product;
import com.example.onlineshopdemo.entity.ProductOwner;
import com.example.onlineshopdemo.enumerations.Category;
import com.example.onlineshopdemo.enumerations.Subcategory;
import com.example.onlineshopdemo.service.ProductOwnerService;
import com.example.onlineshopdemo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prodOwner")
public class ProductOwnerController {
    @Autowired
    ProductOwnerService service;
    @Autowired
    ProductService productService;

    ///////////////////////SIGN IN/////////////////////////
    @GetMapping("/productOwnerForm")
    public ModelAndView showProductOwnerForm(HttpSession session) {
        ProductOwner productOwner = new ProductOwner();
        ModelAndView modelAndView = new ModelAndView("productOwnerLogin.html");
        modelAndView.addObject("productOwner", productOwner);
        String err=(String) session.getAttribute("error");
        if (err!=null) {
            modelAndView.addObject("error", err);
            System.out.println(err);
        }
        return modelAndView;
    }

    @PostMapping("/signIn")
    public String  signInProcessing(@ModelAttribute("productOwner") ProductOwner productOwner,HttpSession session) {
        ProductOwner owner=service.getProductOwnerByEmail(productOwner.getEmail());
        if (owner==null){
            session.setAttribute("error","Email Does not exist");
            return "redirect:/prodOwner/productOwnerForm";
        }
        if (!owner.getPassword().equals(productOwner.getPassword())){
            session.setAttribute("error","invalid password");
            return "redirect:/prodOwner/productOwnerForm";
        }
        session.setAttribute("ownerLoggedIn",owner);
        return "redirect:/prodOwner/productOwnerHome";
    }
    ///////////////////////////////////////////SIGN UP////////////////////////////////////////
    @GetMapping("/signUp")
    public ModelAndView signUpForm(HttpSession session) {
        ModelAndView modelAndView=new ModelAndView("productOwnerRegister");
        modelAndView.addObject("productOwner",new ProductOwner());
        modelAndView.addObject("error",(String) session.getAttribute("error"));
        return modelAndView;
    }

    @PostMapping("/signUpProcessing")
    public String submitSignUpForm(@ModelAttribute("productOwner")ProductOwner productOwner,HttpSession session){
        String success=service.createProductOwner(productOwner);
        if (success.equals(Defaults.SUCCESS)){
            ProductOwner owner=service.getProductOwnerByEmail(productOwner.getEmail());
            owner.setProductList(new ArrayList<>());
            session.setAttribute("ownerLoggedIn",owner);
            session.setAttribute("error",null);
            return "redirect:/prodOwner/productOwnerHome";
        }
        session.setAttribute("error","email already taken");
        return "redirect:/prodOwner/signUp";
    }
    /////////////////////////////////////////////////HOME/////////////////////////////////////////////
    @GetMapping("/productOwnerHome")
    public ModelAndView home(HttpSession session) {
        ModelAndView mav = new ModelAndView("productOwnerHomepage.html");
        ProductOwner productOwner = (ProductOwner) session.getAttribute("ownerLoggedIn");
        List<Product> products = new ArrayList<>(productOwner.getProductList());
        mav.addObject("productOwner", productOwner);
        mav.addObject("productList", products);
        mav.addObject("NewProduct", new Product());
        mav.addObject("subCategories", Subcategory.values());
        String err=(String) session.getAttribute("error");
        String delErr=(String) session.getAttribute("deleteError");
        String editErr=(String) session.getAttribute("updateError");
        mav.addObject("error",err);
        mav.addObject("delError",delErr);
        mav.addObject("updateError",editErr);
        return mav;
    }

    /////////////////////////////ADD PRODUCT/////////////////////////////////////
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("NewProduct") Product product, HttpSession session){
        ProductOwner productOwner = (ProductOwner) session.getAttribute("ownerLoggedIn");
        product.setProductCategory(product.getProductSubcategory().getCategory());
        String finalImage="/static/images/img/productImages/"+ product.getProductSubcategory().name().toLowerCase()+"/"+product.getProductImage1();
        product.setProductImage1(finalImage);
        Product ret= productService.addProduct(product,productOwner.getId());
        return "redirect:/prodOwner/update";
    }
    @GetMapping("/update")
    public String refresh(HttpSession session){
        ProductOwner productOwner = (ProductOwner) session.getAttribute("ownerLoggedIn");
        ProductOwner prod=service.getProductOwnerByEmail(productOwner.getEmail());
        session.setAttribute("ownerLoggedIn",prod);
        return "redirect:/prodOwner/productOwnerHome";
    }
    /////////////////////////////////////EDIT PRODUCT///////////////////////////////////////////////////
    @PostMapping("/edit")
    public ModelAndView editSetup(@RequestParam("prodId") long productID,HttpSession session){
        ProductOwner productOwner = (ProductOwner) session.getAttribute("ownerLoggedIn");
        List<Product> productList=productOwner.getProductList();
        ModelAndView modelAndView=new ModelAndView();
        for (Product p:productList) {
            if (p.getId()==productID) {
                modelAndView.addObject("product", productService.getProduct(productID));
                modelAndView.addObject("subCategories",Subcategory.values());
                session.setAttribute("prodID",productID);
                modelAndView.setViewName("productOwnerEditProduct.html");
                session.setAttribute("updateError",null);
                return modelAndView;
            }
        }
        session.setAttribute("updateError","It seems you do not own this product");
        modelAndView.setViewName("redirect:/prodOwner/update");
        return modelAndView;
    }
    @PostMapping("/editProduct")
    public String editSetup(@ModelAttribute("productI") Product product,HttpSession session){
        System.out.println("EditProd:  "+ product.toString());
        ProductOwner productOwner = (ProductOwner) session.getAttribute("ownerLoggedIn");
        product.setProductOwner(productOwner);
        String finalImage="/static/images/img/productImages/"+ product.getProductSubcategory().name().toLowerCase()+"/"+product.getProductImage1();
        product.setProductImage1(finalImage);
        product.setProductCategory(product.getProductSubcategory().getCategory());
        productService.updateProduct((long)session.getAttribute("prodID"),product);

        return "redirect:/prodOwner/update";
    }
    ////////////////////////////////DELETE//////////////////////////////////////////
    @GetMapping("/delete/{productId}")
    public String delete(@PathVariable("productId") long prodId,HttpSession session) {
        System.out.print("deleting product: ");
        ProductOwner productOwner = (ProductOwner) session.getAttribute("ownerLoggedIn");
        List<Product> productList=productOwner.getProductList();
        int i;
        for (i=0;i<productList.size();i++) {
            if (productList.get(i).getId()==prodId) {
                productService.deleteProduct(productOwner.getId(),prodId);
                session.setAttribute("deleteError",null);
                return "redirect:/prodOwner/update";
            }
        }
        session.setAttribute("deleteError","It seems you do not own this product");
        return "redirect:/prodOwner/productOwnerHome";
    }
    ///////////////////////////////LOG-OUT///////////////////////////////////////////////
    @GetMapping("/logout")
    public String logout(HttpSession session){
        //probably do other things sha
        session.invalidate();
        return "redirect:/prodOwner/productOwnerForm";
    }

}