package com.mpf.sell.service;

import com.mpf.sell.dataobject.ProductCategory;
import java.util.List;

/**
 * 商品类目的service
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryType);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeList(List<Integer> categoryList);

    ProductCategory save(ProductCategory productCategory);
}
