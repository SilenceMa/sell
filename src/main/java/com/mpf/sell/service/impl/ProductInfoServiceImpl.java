package com.mpf.sell.service.impl;

import com.mpf.sell.dao.ProductInfoDao;
import com.mpf.sell.dataobject.ProductInfo;
import com.mpf.sell.dto.CartDto;
import com.mpf.sell.enums.ProductStatusEnums;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.service.ProductInfoService;
import com.mpf.sell.service.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoDao productInfoDao;

    @Autowired
    private RedisLock redisLock;

    public static final long TIMEOUT = 10*10000;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll(Integer productStatus) {
        return productInfoDao.findByProductStatus(productStatus);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {

        return productInfoDao.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cart : cartDtoList) {
            ProductInfo productInfo = productInfoDao.findOne(cart.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = cart.getProductQuantity() + productInfo.getProductStock();
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {

        long time = System.currentTimeMillis() + TIMEOUT;

        for (CartDto c : cartDtoList) {
            //在减库存之前先加锁
            if(!redisLock.lock(c.getProductId(),String.valueOf(time))){
                throw new SellException(ResultEnum.REDIS_LOCK_ERROR);
            }


            ProductInfo one = productInfoDao.findOne(c.getProductId());
            if (one == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }

            Integer result = one.getProductStock() - c.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_NOT_ENOUGH);
            }
            one.setProductStock(result);
            productInfoDao.save(one);

            //解锁
            redisLock.unlock(c.getProductId(),String.valueOf(time));
        }
    }

    /**
     * 上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSell(String productId) {
        //1.查询商品信息
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        //2.判断商品的上下架状态
        if (productInfo.getProductStatusEnums() == ProductStatusEnums.ON_LINE){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR );
        }
        //3.修改商品状态为上架状态
        productInfo.setProductStatus(ProductStatusEnums.ON_LINE.getCode());
        ProductInfo save = productInfoDao.save(productInfo);
        return save;
    }

    /**
     * 下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSell(String productId) {
        //1.查询商品信息
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        //2.判断商品的上下架状态
        if (productInfo.getProductStatusEnums() == ProductStatusEnums.DOWN_LINE){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR );
        }
        //2.修改商品状态为上架状态
        productInfo.setProductStatus(ProductStatusEnums.DOWN_LINE.getCode());
        ProductInfo save = productInfoDao.save(productInfo);
        return save;
    }


}
