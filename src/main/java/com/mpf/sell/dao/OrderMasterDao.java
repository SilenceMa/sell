package com.mpf.sell.dao;

import com.mpf.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMasterDao extends JpaRepository<OrderMaster,String>{
    /**
     * 查询用户订单 分页查找
     * @param customerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByCustomerOpenid(String customerOpenid, Pageable pageable);
}
