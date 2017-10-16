package com.mpf.sell.dao;

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
public class SellerInfoDaoTest {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void save() throws Exception{
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId("601");
        sellerInfo.setUsername("10000001");
        sellerInfo.setPassword("123");
        sellerInfo.setOpenid("asfdafsadf12");

        SellerInfo save = sellerInfoDao.save(sellerInfo);
        Assert.assertEquals("601",save.getId());
    }

    @Test
    public void findByOpenid() throws Exception {
        SellerInfo openid = sellerInfoDao.findByOpenid("asfdafsadf12");
        Assert.assertEquals("asfdafsadf12",openid.getOpenid());
    }

}