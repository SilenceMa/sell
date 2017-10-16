package com.mpf.sell.service.impl;

import com.mpf.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals(1, (Object) category.getCategoryId());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> categoryList = categoryService.findAll();
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    public void findByCategoryTypeList() throws Exception {
        List<Integer> list = Arrays.asList(1,2,3);
        List<ProductCategory> categoryTypeList = categoryService.findByCategoryTypeList(list);
        Assert.assertNotEquals(0,categoryTypeList.size());

    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("男生最爱",2);
        ProductCategory save = categoryService.save(productCategory);
        Assert.assertEquals(2,(Object)save.getCategoryType());
    }

}