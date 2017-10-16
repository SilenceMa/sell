package com.mpf.sell.dao;

import com.mpf.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao masterDao;

    @Test
    public void save() throws Exception{
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1001");
        orderMaster.setCustomerName("张三");
        orderMaster.setCustomerPhone("17732212834");
        orderMaster.setCustomerOpenid("12331243245");
        orderMaster.setCustomerAddress("陕西省渭南市富平县");
        orderMaster.setOrderAmount(new BigDecimal(599));
        OrderMaster save = masterDao.save(orderMaster);
        Assert.assertNotEquals(null,save);
    }


    @Test
    public void findByCustomerOpenid() throws Exception {
        PageRequest request = new PageRequest(0,3);
        Page<OrderMaster> customerOpenid = masterDao.findByCustomerOpenid("12331243245", request);
        Assert.assertEquals("1001",customerOpenid.getContent().get(0).getOrderId());

    }

}