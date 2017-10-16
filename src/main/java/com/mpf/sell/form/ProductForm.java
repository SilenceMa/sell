package com.mpf.sell.form;

import com.mpf.sell.enums.ProductStatusEnums;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductForm {
    private String productId;
    /*商品名称*/
    private String productName;
    /*单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*商品描述*/
    private String productDescription;
    /*商品图片*/
    private String productIcon;
    /*类目编号*/
    private Integer categoryType;
    /*状态0正常1下架*/
    private Integer productStatus = ProductStatusEnums.ON_LINE.getCode();
}
