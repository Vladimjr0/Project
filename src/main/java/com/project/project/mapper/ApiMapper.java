package com.project.project.mapper;

import com.project.project.dtos.*;
import com.project.project.models.Category;
import com.project.project.models.Product;
import com.project.project.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
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
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "categories", ignore = true)
    Product productAddDtoToProduct(ProductAddDto productAddDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateProductFromDto(ProductAddDto productAddDto, @MappingTarget Product product);


    @Mapping(target = "userPassword", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User registrationUserDtoToUser(RegistrationUserDto registrationUserDto);


    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserUpdateDto userUpdateDto, @MappingTarget User user);

    UserDto userToUserDto(User user);



}