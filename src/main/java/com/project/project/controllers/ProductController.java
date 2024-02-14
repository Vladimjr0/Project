package com.project.project.controllers;


import com.project.project.models.Product;
import com.project.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/addproduct")
    public String showAddProduct(Model model) {
        model.addAttribute("product", new Product());
        return "addproduct";
    }

    @PostMapping("/addproduct")
    public String addNewProduct(@ModelAttribute Product product) {
        if (productService.addProduct(product)) {
            return "redirect:/success";
        } else {
            return "redirect:/notSuccess";
        }
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/removeproduct")
    public String removeProduct(Model model) {
        model.addAttribute("itemName", "");
        return "removeproduct";
    }

    @PostMapping("/removeproduct")
    public String removeProduct(@RequestParam("itemName") String itemName) {
        if (productService.removeProduct(itemName)) {
            return "redirect:/success";
        } else {
            return "redirect:/notSuccess";
        }
    }


    @GetMapping("/success")
    public String showSuccess() {
        return "success";
    }

    @GetMapping("/notSuccess")
    public String showNotSuccess() {
        return ("notSuccess");
    }


}
