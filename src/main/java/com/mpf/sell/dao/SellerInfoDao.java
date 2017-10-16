package com.mpf.sell.dao;

import com.mpf.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoDao extends JpaRepository<SellerInfo,String> {
    SellerInfo findByOpenid(String openid);
}
