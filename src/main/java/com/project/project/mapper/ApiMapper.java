package com.project.project.mapper;

import com.project.project.dtos.ProductsResponseDto;
import com.project.project.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);

    ProductsResponseDto productToProductResponseDto(Product product);

}