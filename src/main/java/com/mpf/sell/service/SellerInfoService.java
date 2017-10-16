package com.mpf.sell.service;

import com.mpf.sell.dataobject.SellerInfo;

public interface SellerInfoService {
    SellerInfo findSellerByOpenid(String openid);
}
