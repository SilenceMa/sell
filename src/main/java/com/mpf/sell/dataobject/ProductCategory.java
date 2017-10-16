package com.mpf.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目表的映射对象
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    /*类目id
    * 主键用Id表示
    * GeneratedValue 表示自增
    * */
    @Id
    @GeneratedValue
    private Integer categoryId;
    /*类目名称*/
    private String categoryName;
    /*类目编号*/
    private Integer categoryType;
    /*创建时间*/
    private Date createTime;
    /*更新时间*/
    private Date updateTime;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory() {
    }
}

