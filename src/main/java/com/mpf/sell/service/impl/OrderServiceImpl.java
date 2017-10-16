package com.mpf.sell.service.impl;

import com.mpf.sell.converter.OrderMaster2OrderDto;
import com.mpf.sell.dao.OrderDetailDao;
import com.mpf.sell.dao.OrderMasterDao;
import com.mpf.sell.dataobject.OrderDetail;
import com.mpf.sell.dataobject.OrderMaster;
import com.mpf.sell.dataobject.ProductInfo;
import com.mpf.sell.dto.CartDto;
import com.mpf.sell.dto.OrderDto;
import com.mpf.sell.enums.OrderStatusEnum;
import com.mpf.sell.enums.PayStatusEnum;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.service.OrderService;
import com.mpf.sell.service.ProductInfoService;
import com.mpf.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailDao detailDao;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        String orderId = KeyUtils.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
//        List<CartDto> cartDtoList = new ArrayList<>();
        //1.查询商品(数量、价格)
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDto.getOrderDetails()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }


            //2.计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            productInfoList.add(productInfo);

            //3.订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            orderDetail.setOrderId(orderId);
            detailDao.save(orderDetail);
            /*CartDto cartDto = new CartDto(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDtoList.add(cartDto);*/
        }


        //3.写入订单(OrderMaster和OrderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
//        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //4.扣库存
                /*List<Integer> categoryList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());*/

        List<CartDto> cartDtoList = orderDto.getOrderDetails().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtoList);
        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {
        //1.查询订单
        OrderMaster master = orderMasterDao.findOne(orderId);
        if (master == null) {
            throw new SellException(ResultEnum.ORDER_MASTER_NOT_EXIT);
        }
        //查询订单详情
        List<OrderDetail> orderDetailList = detailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_MASTER_NOT_EXIT);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(master, orderDto);
        orderDto.setOrderDetails(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findAll(String customerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterList = orderMasterDao.findByCustomerOpenid(customerOpenid, pageable);
        List<OrderDto> orderDtoList = OrderMaster2OrderDto.convert(orderMasterList.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<OrderDto>(orderDtoList, pageable, orderMasterList.getTotalElements());
        return orderDtoPage;
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        //1.判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】 订单状态不正确 orderId = {},orderStatus = {}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster masterResult = orderMasterDao.save(orderMaster);
        if (masterResult == null) {
            log.error("【取消订单】更新失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //3.返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetails())) {
            log.error("【取消订单】订单中无商品，orderDto = {}", orderDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetails().stream().
                map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);
        //4.已支付需要退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.FINISH)) {
            //todo

        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        // 1.判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】 订单状态有误 orderId = {},orderDetail = {}", orderDto.getOrderId(), orderDto.getOrderDetails());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDto.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);

        OrderMaster save = orderMasterDao.save(orderMaster);
        if (save == null) {
            log.error("【取消订单】更新失败，orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //2.修改订单状态
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto pay(OrderDto orderDto) {
        //1.判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付成功】 订单状态有误 orderId = {},orderDetail = {}", orderDto.getOrderId(), orderDto.getOrderDetails());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断你支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付成功】 订单支付状态有误 orderDto = {}", orderDto);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //3.修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setPayStatus(PayStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster save = orderMasterDao.save(orderMaster);
        if (save == null) {
            log.error("【订单支付成功】 更新失败 orderDto = {}", orderDto);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterList = orderMasterDao.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMaster2OrderDto.convert(orderMasterList.getContent());
        Page<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList, pageable, orderMasterList.getTotalElements());
        return orderDtoPage;
    }

}
