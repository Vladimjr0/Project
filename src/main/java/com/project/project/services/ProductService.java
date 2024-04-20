package com.project.project.services;

import com.project.project.dtos.ProductAddDto;
import com.project.project.dtos.ProductsResponseDto;
import com.project.project.entities.Category;
import com.project.project.entities.Product;
import com.project.project.mapper.ApiMapper;
import com.project.project.repositories.CategoryRepository;
import com.project.project.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
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

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll(Sort.by("itemName"));
        return products.stream()
                .map(ApiMapper.INSTANCE::productToProductResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductsResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        return ApiMapper.INSTANCE.productToProductResponseDto(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    //TODO подумать над тем, что должен возвращать этот метод
    public void addCategoryToProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
        product.getCategories().add(category);
        productRepository.save(product);

    }

    @Transactional
    public void removeCategoryFromProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
        product.getCategories().remove(category);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> sortProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));

        List<Product> products = productRepository.findByCategoriesContaining(category);

        return products.stream()
                .map(ApiMapper.INSTANCE::productToProductResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional
    public ProductsResponseDto updateProduct(ProductAddDto productAddDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        ApiMapper.INSTANCE.updateProductFromDto(productAddDto, product);

        productRepository.save(product);

        return ApiMapper.INSTANCE.productToProductResponseDto(product);

    }
}





