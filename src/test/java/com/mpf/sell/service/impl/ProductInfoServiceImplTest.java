package com.mpf.sell.service.impl;

import com.mpf.sell.dataobject.ProductInfo;
import com.mpf.sell.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    private String productId ="10003";
    @Test
    public void findOne() throws Exception {
        ProductInfo one = productInfoService.findOne("10003");
        Assert.assertEquals(0,(Object)one.getProductStatus());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductInfo> all = productInfoService.findUpAll(ProductStatusEnums.ON_LINE.getCode());
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findUpAll() throws Exception {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> all = productInfoService.findAll(request);
        System.out.println(all.getSize());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo("10004","生煎",
                new BigDecimal(5.0),100,"色香味俱全",
                "http:ddd.jpg",2,0);
        ProductInfo save = productInfoService.save(productInfo);
        Assert.assertEquals(0,(Object)save.getProductStatus());
    }

    @Test
    public void onSell() throws Exception{
        ProductInfo productInfo = productInfoService.onSell(productId);
        Assert.assertEquals(ProductStatusEnums.ON_LINE.getCode(),productInfo.getProductStatus());
    }

    @Test
    public void offSell() throws Exception{
        ProductInfo productInfo = productInfoService.offSell(productId);
        Assert.assertEquals(ProductStatusEnums.DOWN_LINE.getCode(),productInfo.getProductStatus());
    }
}