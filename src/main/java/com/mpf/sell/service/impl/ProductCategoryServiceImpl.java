package com.mpf.sell.service.impl;

import com.mpf.sell.dao.ProductCategoryDao;
import com.mpf.sell.dataobject.ProductCategory;
import com.mpf.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类目service的impl
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

    @Autowired
    private ProductCategoryDao categoryDao;

    /**
     * 根据商品类目类型查询商品类目信息
     * @param categoryType 商品类目类型
     * @return 商品类目信息
     */
    @Override
    public ProductCategory findOne(Integer categoryType) {
        return categoryDao.findOne(categoryType);
    }

    /**
     * 查询所有商品信息
     * @return 所有商品类目信息集合
     */
    @Override
    public List<ProductCategory> findAll() {
        return categoryDao.findAll();
    }

    /**
     * 根据商品类目类型集合查询商品类目类型信息集合
     * @param categoryList 商品类目类型集合
     * @return 品类目类型信息集合
     */
    @Override
    public List<ProductCategory> findByCategoryTypeList(List<Integer> categoryList) {
        return categoryDao.findByCategoryTypeIn(categoryList);
    }

    /**
     * 添加或更新商品类目信息
     * @param productCategory 商品类目信息
     * @return 商品类目信息
     */
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDao.save(productCategory);
    }
}
