package com.mpf.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mpf.sell.enums.ProductStatusEnums;
import com.mpf.sell.utils.EnumUtils;
import com.mpf.sell.utils.serialize.Date2LongSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {
    @Id
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
    private Integer productStatus;
    /*创建时间*/
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date createTime;
    /*更新时间*/
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date updateTime;

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Integer categoryType, Integer productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.categoryType = categoryType;
        this.productStatus = productStatus;
    }

    public ProductInfo() {
    }
    @JsonIgnore
    public ProductStatusEnums getProductStatusEnums(){
        return EnumUtils.getByCode(productStatus,ProductStatusEnums.class);
    }
}
