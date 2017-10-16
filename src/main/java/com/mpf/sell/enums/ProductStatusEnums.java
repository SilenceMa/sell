package com.mpf.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 */
@Getter
public enum ProductStatusEnums implements CodeEnum{
    ON_LINE(0,"上架中"),
    DOWN_LINE(1,"商品已下架")
    ;
    private Integer code;
    private String message;

    ProductStatusEnums(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
