package com.mpf.sell.dao;

import com.mpf.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void findOneTest() throws Exception {
        ProductCategory productCategory = productCategoryDao.findOne(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest() throws Exception {
        ProductCategory productCategory = productCategoryDao.findOne(1);
        productCategory.setCategoryType(1);
        productCategoryDao.save(productCategory);
    }

    /**
     * Transactional这里的事物是javax的事物，测试完成回滚，数据库中的数据不会发生改变
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void updateTest() throws Exception {
        ProductCategory productCategory = new ProductCategory("男生最爱", 4);
        productCategory.setCategoryId(2);
        productCategoryDao.save(productCategory);
    }

    @Test
    public void testFindByCategoryTypeList() throws Exception {
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = Arrays.asList(1,3);
        list.add(1);
        list.add(3);
        List<ProductCategory> productCategoryList = productCategoryDao.findByCategoryTypeIn(list1);
        System.out.println(productCategoryList.toString());
    }
}