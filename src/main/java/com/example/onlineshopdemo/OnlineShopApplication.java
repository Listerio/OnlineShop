package com.example.onlineshopdemo;

import com.example.onlineshopdemo.service.ProductService;
import com.example.onlineshopdemo.service.impl.DefaultProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShopApplication.class, args);
	}

}
