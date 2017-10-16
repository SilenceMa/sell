package com.mpf.sell.dao;

import com.mpf.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail,String>{
    /**
     * 根据订单id查询订单明细信息
     * @param orderId 订单 id
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
