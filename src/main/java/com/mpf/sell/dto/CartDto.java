package com.mpf.sell.dto;

import lombok.Data;

@Data
public class CartDto {
    /*商品的id*/
    private String productId;
    /*商品的数量*/
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDto() {
    }
}
