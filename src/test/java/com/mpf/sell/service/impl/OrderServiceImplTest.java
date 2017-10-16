package com.mpf.sell.service.impl;

import com.mpf.sell.dataobject.OrderDetail;
import com.mpf.sell.dto.CartDto;
import com.mpf.sell.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl service;

    private String openId = "12331243243";
    private String orderId = "1507513950937452771";
    @Test
    public void create() throws Exception {
        //1.订单
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName("田七");
        orderDto.setCustomerAddress("上海市徐汇区");
        orderDto.setCustomerPhone("123457842");
        orderDto.setCustomerOpenid(openId);

        //2.购物车
        List<OrderDetail> orderDetails = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("10004");
        orderDetail.setProductQuantity(10);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("10003");
        orderDetail2.setProductQuantity(20);

        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail2);

        orderDto.setOrderDetails(orderDetails);
        OrderDto result = service.create(orderDto);
        log.info("创建订单 = {}"+result);
        Assert.assertNotEquals(0,result.getOrderId());
    }

    @Test
    public void findOne() throws Exception {
        OrderDto orderDto = service.findOne(orderId);
        log.info("订单详情: orderDto = {}",orderDto);
        Assert.assertEquals(orderId,orderDto.getOrderId());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDto> orderDtoPage = service.findAll(openId, request);
        Assert.assertNotEquals(0,orderDtoPage.getTotalElements());
    }

    /**
     * 取消订单
     * @throws Exception
     */
    @Test
    public void cancel() throws Exception {
        OrderDto orderDto = service.findOne(orderId);
        OrderDto cancel = service.cancel(orderDto);
        Assert.assertEquals(2,(Object)cancel.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDto orderDto = service.findOne(orderId);
        OrderDto finish = service.finish(orderDto);
        Assert.assertEquals(1,(Object)finish.getOrderStatus());
    }

    @Test
    public void pay() throws Exception {
        OrderDto orderDto = service.findOne(orderId);
        OrderDto pay = service.pay(orderDto);
        Assert.assertEquals(1,(Object)pay.getPayStatus());
    }

    @Test
    public void findList() throws Exception{
        PageRequest pageRequest = new PageRequest(0,5);
        Page<OrderDto> orderDtos = service.findList(pageRequest);
        log.info("【分页查询全部订单】 orderDtos = {}",orderDtos);
        Assert.assertNotEquals(0,orderDtos.getTotalElements());
    }

}