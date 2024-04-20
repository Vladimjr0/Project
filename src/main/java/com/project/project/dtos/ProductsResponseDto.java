package com.project.project.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductsResponseDto {

    private Long id;
    private String itemName;
    private Double itemPrice;
    private int itemQuantity;
    private List<String> categoryNames;


}
