package com.mpf.sell.service;

import com.mpf.sell.dto.OrderDto;

public interface CustomerService {
    //查询单个订单
    OrderDto findOrderByOrderId(String openid,String orderId);

    //取消订单
    OrderDto cancelOrder(String openid,String orderId);
}
