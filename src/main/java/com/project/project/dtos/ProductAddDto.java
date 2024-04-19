package com.project.project.dtos;

import lombok.Data;

@Data
public class ProductAddDto {

    private String itemName;
    private Double itemPrice;
    private Integer itemQuantity;
    private String categoryName;

}
