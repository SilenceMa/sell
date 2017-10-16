package com.mpf.sell.controller;

import com.mpf.sell.dataobject.ProductCategory;
import com.mpf.sell.dataobject.ProductInfo;
import com.mpf.sell.enums.ProductStatusEnums;
import com.mpf.sell.service.ProductCategoryService;
import com.mpf.sell.service.ProductInfoService;
import com.mpf.sell.utils.ResultUtils;
import com.mpf.sell.vo.ProductInfoVo;
import com.mpf.sell.vo.ProductVo;
import com.mpf.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping(value = "/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVo list() throws Exception {
        /*1.查询所有上架商品*/
        List<ProductInfo> productInfoList = productInfoService.findUpAll(ProductStatusEnums.ON_LINE.getCode());
        /*2.查询类目*/
        //原始的装填方式
        /*List<Integer> categoryList = new ArrayList<>();
        for (ProductInfo productInfo:
                productInfoServiceUpAll) {
            categoryList.add(productInfo.getCategoryType());
        }*/
        //java8 lambda 拿到类目编码 集合
        List<Integer> categoryList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryTypeList = productCategoryService.findByCategoryTypeList(categoryList);
        /*3.数据拼接*/
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : categoryTypeList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVo(productInfoVoList);
            productVoList.add(productVo);
        }

        return ResultUtils.success(productVoList);
    }
}
