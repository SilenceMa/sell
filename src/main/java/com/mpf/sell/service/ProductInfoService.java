package com.mpf.sell.service;

import com.mpf.sell.dataobject.ProductInfo;
import com.mpf.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll(Integer productStatus);

    /**
     * 分页查询商品信息
     * @param pageable 页数
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDto> cartDtoList);

    //减库存
    void decreaseStock(List<CartDto> cartDtoList);

    //上架
    ProductInfo onSell(String productId);

    //下架
    ProductInfo offSell(String productId);
}
