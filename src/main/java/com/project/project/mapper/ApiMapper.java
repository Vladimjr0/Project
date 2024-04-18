package com.project.project.mapper;

import com.project.project.dtos.ProductsResponseDto;
import com.project.project.dtos.UserDto;
import com.project.project.models.Category;
import com.project.project.models.Product;
import com.project.project.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);

    @Mapping(source = "categories", target = "categoryNames", qualifiedByName = "categoriesToNames")
    ProductsResponseDto productToProductResponseDto(Product product);

    @Named("categoriesToNames")
    default List<String> categoriesToNames(Collection<Category> categories){
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    UserDto userToUserDto(User user);



}