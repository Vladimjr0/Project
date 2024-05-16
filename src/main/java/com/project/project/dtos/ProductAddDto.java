package com.project.project.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductAddDto {

    @NotBlank(message = "Это поле обязательное")
    @Size(min = 3, max = 100, message = "Название товара должно содержать от 3 до 100 символов")
    private String itemName;

    @NotNull(message = "Это поле обязательное")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена товара должна быть больше нуля")
    private Double itemPrice;

    @NotNull(message = "Это поле обязательное")
    @Min(value = 0, message = "Количество товара должно быть неотрицательным числом")
    @Max(value = 100, message = "Количество товара ее должно превышать 100 единиц")
    private Integer itemQuantity;

    @NotBlank(message = "Это поле обязательное")
    @Size(min = 3, max = 100, message = "Название товара должно содержать от 3 до 100 символов")
    private String categoryName;

}
