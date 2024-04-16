package com.project.project.mapper;

import com.project.project.dtos.ProductsResponseDto;
import com.project.project.dtos.UserDto;
import com.project.project.models.Product;
import com.project.project.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);

    ProductsResponseDto productToProductResponseDto(Product product);
    UserDto userToUserDto(User user);

}