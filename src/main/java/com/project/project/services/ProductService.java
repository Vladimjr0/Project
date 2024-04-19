package com.project.project.services;

import com.project.project.dtos.ProductAddDto;
import com.project.project.dtos.ProductsResponseDto;
import com.project.project.mapper.ApiMapper;
import com.project.project.models.Category;
import com.project.project.models.Product;
import com.project.project.repositories.CategoryRepository;
import com.project.project.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductsResponseDto createProduct(@RequestBody ProductAddDto productAddDto) {
        if (productRepository.existsByItemName(productAddDto.getItemName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Товар с таким именем уже существует");
        }
        Product product = ApiMapper.INSTANCE.productAddDtoToProduct(productAddDto);
        product.setCategories(List.of(
                categoryRepository.findByName(productAddDto.getCategoryName()).orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такой категории не существует"))
        ));

        productRepository.save(product);

        return ApiMapper.INSTANCE.productToProductResponseDto(product);

    }

    public List<ProductsResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll(Sort.by("itemName"));
        return products.stream()
                .map(ApiMapper.INSTANCE::productToProductResponseDto)
                .collect(Collectors.toList());
    }

    public ProductsResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        return ApiMapper.INSTANCE.productToProductResponseDto(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден");
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


    public ProductsResponseDto updateProduct(ProductAddDto productAddDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        ApiMapper.INSTANCE.updateProductFromDto(productAddDto, product);

        productRepository.save(product);

        return ApiMapper.INSTANCE.productToProductResponseDto(product);

    }
}





