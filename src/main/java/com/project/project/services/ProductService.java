package com.project.project.services;

import com.project.project.dtos.ProductAddDto;
import com.project.project.dtos.ProductsResponseDto;
import com.project.project.mapper.ApiMapper;
import com.project.project.models.Category;
import com.project.project.models.Product;
import com.project.project.repositories.CategoryRepository;
import com.project.project.repositories.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductsResponseDto createProduct(@RequestBody ProductAddDto productAddDto) {
        if (productRepository.existsByItemName(productAddDto.getItemName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с таким именем уже существует");
        }
        Product product = new Product();
        return productFieldSet(product, productAddDto);
    }

    public List<ProductsResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll(Sort.by("itemName"));
        return products.stream()
                .map(ApiMapper.INSTANCE::productToProductResponseDto)
                .collect(Collectors.toList());
    }

    public ProductsResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
        return ApiMapper.INSTANCE.productToProductResponseDto(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        productRepository.deleteById(id);
    }

    //TODO подумать над тем, что должен возвращать этот метод
    public void addCategoryToProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
        product.getCategories().add(category);
        productRepository.save(product);

    }


    public void removeCategoryFromProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
        product.getCategories().remove(category);
        productRepository.save(product);
    }


    public List<ProductsResponseDto> sortProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));

        List<Product> products = productRepository.findByCategoriesContaining(category);

        return products.stream()
                .map(ApiMapper.INSTANCE::productToProductResponseDto)
                .collect(Collectors.toList());

    }


//TODO зачем покупать товар, если должен быть реализован функционал корзины.
//    public ResponseEntity<?> buyProduct(Long id, int itemQuantity) {
//        return productRepository.findById(id)
//                .map(product -> {
//                    if (product.getItemQuantity() >= itemQuantity) {
//                        product.setItemQuantity(product.getItemQuantity() - itemQuantity);
//                        productRepository.save(product);
//                        return ResponseEntity.ok("Покупка успешно совершена");
//                    }
//                    return ResponseEntity.badRequest().body("Нет такого количества товара на складе ");
//                }).orElse(new ResponseEntity<>("Продукт с таким id не найден", HttpStatus.NOT_FOUND));
//    }


    public ProductsResponseDto updateProduct(ProductAddDto productAddDto, Long id) {
        return productRepository.findById(id)
                .map(product -> productFieldSet(product, productAddDto))
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
    }

    //TODO использовать билдер ломбука или же mapstruct
    private ProductsResponseDto productFieldSet(Product product, ProductAddDto productAddDto) {
        product.setItemName(productAddDto.getItemName());
        product.setItemPrice(productAddDto.getItemPrice());
        product.setItemQuantity(productAddDto.getItemQuantity());
        product.setCreatedAt(LocalDateTime.now());
        product.setCategories(List.of(
                categoryRepository.findByName(productAddDto.getCategoryName()).orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такой категории не существует"))
        ));
        productRepository.save(product);
        return new ProductsResponseDto(product.getId(),
                product.getItemName(),
                product.getItemPrice(),
                product.getItemQuantity(),
                product.getCategories().stream().map(Category::getName).collect(Collectors.toList()));
    }

}


