package com.mpf.sell.service.impl;
import com.mpf.sell.dto.OrderDto;
import com.mpf.sell.enums.OrderStatusEnum;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.service.CustomerService;
import com.mpf.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private OrderService orderService;


    @Override
    public OrderDto findOrderByOrderId(String openid, String orderId) {
        return checkOwner(openid, orderId);
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkOwner(openid, orderId);
        if (orderDto == null) {
            log.error("【取消订单】 查询不到该订单 orderId = {}",orderId);
            throw new SellException(ResultEnum.ORDER_MASTER_NOT_EXIT);
        }
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderDto dto = orderService.cancel(orderDto);
        return dto;
    }

    private OrderDto checkOwner(String openid, String orderId) {
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            return null;
        }
        //判断您当前订单是否为自己的订单
        if (!orderDto.getCustomerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询单个订单】 订单的openid不一致 openid = {},orderDto = {}", openid, orderDto);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
