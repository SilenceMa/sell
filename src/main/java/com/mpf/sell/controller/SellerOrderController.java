package com.mpf.sell.controller;

import com.mpf.sell.dto.OrderDto;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家订单的controller
 */
@Controller
@RequestMapping(value = "/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService service;


    /**
     * 订单列表
     *
     * @param page 第几页 (默认从第一页开始)
     * @param size 每页多少条数据
     * @return
     */
    @GetMapping(value = "/list")
    public ModelAndView list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDto> orderDtos = service.findList(request);
        map.put("orderDtos", orderDtos);
        map.put("currentPage", page);
        map.put("totalPages", orderDtos.getTotalPages());
        map.put("size", size);
        log.info("totalPages={}", orderDtos.getTotalPages());
        return new ModelAndView("order/list");
    }

    /**
     * 取消订单
     * @param orderId 订单id
     * @param map 将要传递的数据
     * @return
     */
    @GetMapping(value = "/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {

        try {
            OrderDto orderDto = service.findOne(orderId);
            service.cancel(orderDto);
        } catch (SellException e) {
            log.error("【卖家取消订单】 查询订单失败={}", e);
            map.put("message", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("message", ResultEnum.SUCCESS_CANCEL.getMessage());
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success");
    }

    @GetMapping(value = "/detail")
    public ModelAndView detail(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map){
        OrderDto orderDto;
        try {
            orderDto= service.findOne(orderId);
        } catch (SellException e) {
            log.error("【卖家查询订单详情】 查询订单失败={}", e);
            map.put("message", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDto",orderDto);
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("order/detail");
    }

    /**
     * 卖家完结订单
     * @param orderId 订单id
     * @param map 界面数据
     * @return
     */
    @GetMapping(value = "/finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map){
        OrderDto orderDto;
        try {
            orderDto= service.findOne(orderId);
            service.finish(orderDto);
        } catch (SellException e) {
            log.error("【卖家完结订单详情】 发生异常={}", e);
            map.put("message", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("message",ResultEnum.SUCCESS_FINISH.getMessage());
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success");
    }
}
