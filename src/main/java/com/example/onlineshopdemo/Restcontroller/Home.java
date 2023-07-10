package com.example.onlineshopdemo.Restcontroller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {

    @RequestMapping("/")
    public String home() {
        return "redirect:/demo/shopping/indexPage/home";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        // TODO: 7/4/2023 might need to update something
        session.invalidate();
        return "redirect:/demo/shopping/indexPage/home";
    }

}
