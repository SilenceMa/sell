package com.mpf.sell.dataobject.mapper;

import com.mpf.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;
    @Test
    public void insertIntoCategoryType() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","小学生最爱");
        map.put("category_type",1);
        int i = mapper.insertIntoCategoryType(map);
        Assert.assertEquals(1,i);
    }

    @Test
    public void findByCategoryType() throws Exception{
        ProductCategory categoryType = mapper.findByCategoryType(1);
        Assert.assertEquals(1,(Object)categoryType.getCategoryType());
    }

}