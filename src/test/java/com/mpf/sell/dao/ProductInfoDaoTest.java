package com.mpf.sell.dao;

import com.mpf.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {
    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void findOne() throws Exception{
        ProductInfo one = productInfoDao.findOne("10001");
        Assert.assertEquals(0,(Object)one.getProductStatus());
    }

    @Test
    public void findAll() throws Exception{
        List<ProductInfo> productInfoDaoAll = productInfoDao.findAll();
        Assert.assertNotEquals(0,productInfoDaoAll.size());
    }

    @Test
    public void saveTest() throws Exception{
        ProductInfo productInfo = new ProductInfo("10001","馒头",
                new BigDecimal(1.0),100,"又白又大又甜",
                "http:sss.jpg",2,0);
        ProductInfo save = productInfoDao.save(productInfo);
        Assert.assertEquals("10001",save.getProductId());
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> productStatus = productInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0,productStatus.size());
    }

}