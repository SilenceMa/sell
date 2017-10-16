package com.mpf.sell.controller;

import com.mpf.sell.converter.OrderForm2OrderDto;
import com.mpf.sell.dto.OrderDto;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.form.OrderForm;
import com.mpf.sell.sellexception.ResponseBankException;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.service.CustomerService;
import com.mpf.sell.service.OrderService;
import com.mpf.sell.utils.ResultUtils;
import com.mpf.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class CustomerOrderController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private CustomerService customerService;
    //1.创建订单
    @PostMapping(value = "/create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】 参数错误 ,orderForm = {}", orderForm);
            throw new SellException(ResultEnum.PARAMS_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto dto = OrderForm2OrderDto.convert(orderForm);
        if (CollectionUtils.isEmpty(dto.getOrderDetails())) {
            log.error("【创建订单】 购物车不能为空 dto ={}", dto);
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDto orderDto = orderService.create(dto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderDto.getOrderId());
        return ResultUtils.success(map);
    }

    //2.订单列表
    @PostMapping(value = "/list")
    public ResultVo<List<OrderDto>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //判断openid
        if (StringUtils.isEmpty(openid)) {
            log.error("【订单列表】 openid为空");
            throw new SellException(ResultEnum.OPEN_ID_EMPTY);
        }
        PageRequest request = new PageRequest(page, size);
        Page<OrderDto> orderDtos = orderService.findAll(openid, request);
        return ResultUtils.success(orderDtos.getContent());
    }

    //3.订单详情
    @PostMapping(value = "/detail")
    public ResultVo<OrderDto> detail(@RequestParam(value = "openid") String openid,
                                     @RequestParam(value = "orderId") String orderId
    ) {
        //todo
        //判断opendid 和 orderid
        if (StringUtils.isEmpty(openid)) {
            log.error("【订单详情】 openid为空");
            throw new SellException(ResultEnum.OPEN_ID_EMPTY);
//        throw new ResponseBankException();
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【订单详情】 orderid为空");
            throw new SellException(ResultEnum.ORDER_ID_EMPTY);
        }
        OrderDto orderDto = customerService.findOrderByOrderId(openid, orderId);
        return ResultUtils.success(orderDto);
    }

    //4.取消订单
    @GetMapping(value = "/cancel")
    public ResultVo cancel(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "orderId") String orderId) {
        //判断opendid 和 orderid
        if (StringUtils.isEmpty(openid)) {
            log.error("【订单详情】 openid为空");
            throw new SellException(ResultEnum.OPEN_ID_EMPTY);
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【订单详情】 orderid为空");
            throw new SellException(ResultEnum.ORDER_ID_EMPTY);
        }
        //todo
        customerService.cancelOrder(openid, orderId);
        return ResultUtils.success();

    }
}
