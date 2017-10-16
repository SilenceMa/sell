package com.mpf.sell.service.impl;

import com.mpf.sell.dao.SellerInfoDao;
import com.mpf.sell.dataobject.SellerInfo;
import com.mpf.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoServiceImpl implements SellerInfoService{

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findSellerByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }
}
