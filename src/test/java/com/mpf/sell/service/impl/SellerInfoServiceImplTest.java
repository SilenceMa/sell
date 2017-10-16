package com.mpf.sell.service.impl;

import com.mpf.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoServiceImplTest {
    @Autowired
    private SellerInfoServiceImpl sellerInfoService;

    @Test
    public void findSellerByOpenid() throws Exception {
        String openid = "abc";
        SellerInfo seller = sellerInfoService.findSellerByOpenid(openid);
        Assert.assertEquals("abc", seller.getOpenid());

    }

}