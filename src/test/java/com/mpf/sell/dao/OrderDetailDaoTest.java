package com.mpf.sell.dao;

import com.mpf.sell.dataobject.OrderDetail;
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
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void save() throws Exception{
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("2001");
        orderDetail.setOrderId("1001");
        orderDetail.setProductId("10001");
        orderDetail.setProductName("馒头");
        orderDetail.setProductQuantity(30);
        orderDetail.setProductIcon("http:sss.jpg");
        orderDetail.setProductPrice(new BigDecimal(1));

        orderDetailDao.save(orderDetail);

    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId("1001");
        Assert.assertEquals("2001",orderDetails.get(0).getDetailId());
    }

}