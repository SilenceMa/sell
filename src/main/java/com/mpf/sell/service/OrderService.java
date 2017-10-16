package com.mpf.sell.service;

import com.mpf.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /*创建订单*/
    OrderDto create(OrderDto orderDto);

    /*查询单个订单*/
    OrderDto findOne(String orderId);

    /*查询列表订单*/
    Page<OrderDto> findAll(String customerOpenid, Pageable pageable);

    /*取消订单*/
    OrderDto cancel(OrderDto orderDto);
    /*完结订单*/
    OrderDto finish(OrderDto orderDto);
    /*支付订单*/
    OrderDto pay(OrderDto orderDto);

    /*查询全部列表订单*/
    Page<OrderDto> findList(Pageable pageable);
}
