package com.mpf.sell.dao;

import com.mpf.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {
    /*根据商品状态查询商品信息*/
    List<ProductInfo> findByProductStatus(Integer status);
}
