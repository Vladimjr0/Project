package com.project.project.controllers;


import com.project.project.dtos.ProductAddDto;
import com.project.project.models.Product;
import com.project.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

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
        return productService.removeProductId(id);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<?> buyProduct(@RequestBody Map<String, Integer> requestBody, @PathVariable Long id) {
        return productService.buyProduct(id,  requestBody.get("itemQuantity"));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editProduct(@PathVariable Long id, @RequestBody ProductAddDto productAddDto){
        return productService.editProduct(productAddDto, id);
    }


}
