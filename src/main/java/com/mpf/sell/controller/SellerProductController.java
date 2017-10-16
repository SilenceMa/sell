package com.mpf.sell.controller;

import com.mpf.sell.dataobject.ProductCategory;
import com.mpf.sell.dataobject.ProductInfo;
import com.mpf.sell.form.ProductForm;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.service.ProductCategoryService;
import com.mpf.sell.service.ProductInfoService;
import com.mpf.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService service;

    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 卖家端商品列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping(value = "/list")
    public ModelAndView list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            Map<String, Object> map) throws Exception {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfos = service.findAll(request);
        map.put("productInfos", productInfos);
        map.put("currentPage", page);
        map.put("totalPages", productInfos.getTotalPages());
        map.put("size", size);
        log.info("totalPages={}", productInfos.getTotalPages());
        return new ModelAndView("product/list");
    }

    /**
     * 下架商品
     *
     * @param productId 商品id
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/off_sell")
    public ModelAndView off_sell(@RequestParam(value = "productId") String productId,
                                 Map<String, Object> map) throws Exception {
        try {
            ProductInfo productInfo = service.offSell(productId);
        } catch (SellException e) {
            log.error("【上架商品】 商品状态有误 productId = {}", productId);
            map.put("message", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        map.put("message", "商品已下架");
        return new ModelAndView("common/success", map);
    }

    /**
     * 上架商品
     *
     * @param productId 商品id
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/on_sell")
    public ModelAndView on_sell(@RequestParam(value = "productId") String productId,
                                Map<String, Object> map) throws Exception {
        try {
            ProductInfo productInfo = service.onSell(productId);
        } catch (SellException e) {
            log.error("【上架商品】 商品状态有误 productId = {}", productId);
            map.put("message", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        map.put("message", "商品已上架");
        return new ModelAndView("common/success", map);
    }

    /**
     * 新增和修改接口
     *
     * @param productId 商品id非必传
     * @param map       数据
     * @return 新增或修改界面
     */
    @GetMapping(value = "/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        //判断productId是否为空，来判断是新增操作还是修改操作
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = service.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categories = categoryService.findAll();
        map.put("categories", categories);

        return new ModelAndView("product/index", map);
    }

    /**
     * 保存和更新商品
     *
     * @param productForm   商品的表单信息
     * @param bindingResult 校验的错误信息
     * @param map           向后台传递的参数
     * @return
     */
    @PostMapping(value = "/save")
    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        //1.判断传递过来的参数是否有错误
        if (bindingResult.hasErrors()) {
            log.error("【保存 更新商品】 商品参数错误 productForm = {}", productForm);
            map.put("message", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(productForm.getProductId())){
                productInfo = service.findOne(productForm.getProductId());
            }else {
                productForm.setProductId(KeyUtils.getUniqueKey());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            service.save(productInfo);
        } catch (SellException s) {
            log.error("【保存 更新商品】 保存商品信息出错 productInfo = {}", productInfo);
            map.put("message", s.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        map.put("message", "商品信息修改成功");

        return new ModelAndView("common/success", map);

    }

}
