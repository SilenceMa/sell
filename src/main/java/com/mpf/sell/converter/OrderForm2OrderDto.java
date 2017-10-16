package com.mpf.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mpf.sell.dataobject.OrderDetail;
import com.mpf.sell.dto.OrderDto;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.form.OrderForm;
import com.mpf.sell.sellexception.SellException;
import jdk.nashorn.internal.parser.TokenType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDto {
    public static OrderDto convert(OrderForm orderForm){
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName(orderForm.getName());
        orderDto.setCustomerPhone(orderForm.getPhone());
        orderDto.setCustomerOpenid(orderForm.getOpenid());
        orderDto.setCustomerAddress(orderForm.getAddress());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        /**
         * 将items中的string转换成Gson
         */
        Gson gson = new Gson();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());

        }catch (Exception e){
            log.error("【对象转换】 错误，String = {}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAMS_ERROR);
        }
        orderDto.setOrderDetails(orderDetailList);
        return orderDto;
    }
}
