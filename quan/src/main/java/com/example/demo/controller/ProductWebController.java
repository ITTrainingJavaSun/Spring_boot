package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/addproduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/addproduct")
    public String addProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }
}