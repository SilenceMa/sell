package com.mpf.sell.dao;

import com.mpf.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {
    /**
     * 根据商品类目类型集合查询上商品类目信息集合
     * @param categoryTypeList 商品类目类型集合
     * @return ProductCategory 商品类目信息
     * 这里的查询方法需要遵循jpa的语法规范 在findBy后加实体类中的字段名称再加上in
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
