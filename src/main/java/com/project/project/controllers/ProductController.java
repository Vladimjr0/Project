package com.project.project.controllers;


import com.project.project.dtos.ProductAddDto;
import com.project.project.exceptions.AppError;
import com.project.project.models.Product;
import com.project.project.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/addproduct")
    public ResponseEntity<?> addNewProduct(@RequestBody ProductAddDto productAddDto) {
        return productService.addProduct(productAddDto);
    }

    @GetMapping("/products")
    @ResponseBody
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/removeproduct/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return ResponseEntity.ok("Продукт успешно удален.");
    }

    // ПЕРЕДЕЛАТЬ ПОД RESPONSEENTITY<>
    @GetMapping("/product/{id}")
    @ResponseBody
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
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
