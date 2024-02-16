package com.project.project.controllers;


import com.project.project.models.Product;
import com.project.project.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

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
    public String addNewProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "addproduct";
        }

        if (productService.addProduct(product)) {
            return "redirect:/products";
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

    @GetMapping("/removeproduct/{id}")
    public String removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return "redirect:/products";
    }
//    @PostMapping("/removeproduct")
//    public String removeProduct(@RequestParam("itemName") String itemName) {
//        if (productService.removeProduct(itemName)) {
//            return "redirect:/success";
//        } else {
//            return "redirect:/notSuccess";
//        }
//    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable Long id, Model model){
        model.addAttribute("itemQuantity", "");
        return "buy";
    }

    @PostMapping("/buy/{id}")
    public String buyProduct(@RequestParam("itemQuantity") int itemQuantity, @PathVariable Long id) {
        if (productService.buyProduct(id, itemQuantity)) {
            return "redirect:/products";
        } else {
            return "redirect:/notSuccess";
        }
    }
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "edit";
    }
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @Valid @ModelAttribute Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "edit";
        }
        productService.editProduct(product);
        return "redirect:/products";
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
