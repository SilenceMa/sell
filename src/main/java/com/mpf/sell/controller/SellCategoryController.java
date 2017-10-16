package com.mpf.sell.controller;

import com.mpf.sell.dataobject.ProductCategory;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.form.CategoryForm;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "seller/category")
@Slf4j
public class SellCategoryController {
    @Autowired
    private ProductCategoryService service;

    @GetMapping(value = "/list")
    public ModelAndView list(Map<String, Object> map) throws Exception {
        List<ProductCategory> categoryList = service.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);

    }

    /**
     * 修改商品类目
     *
     * @param categoryId 类目id
     * @param map
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) throws Exception {
        if (categoryId != null) {
            ProductCategory category = service.findOne(categoryId);
            map.put("category", category);
        }

        return new ModelAndView("category/index", map);
    }

    /**
     * 保存类目信息
     *
     * @param categoryForm  类目信息表单
     * @param bindingResult 出错信息
     * @param map
     * @return
     */
    @PostMapping(value = "/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            log.error("【保存类目信息】 保存出错 error = {}", categoryForm);
            map.put("message", ResultEnum.PARAMS_ERROR.getMessage());
            map.put("url", "sell/");
            return new ModelAndView("/sell/seller/category/index", map);
        }
        ProductCategory category = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null) {
                category = service.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,category);
            service.save(category);
        }catch (SellException s){
            log.error("【保存 更新类目】 保存类目信息出错 category = {}", category);
            map.put("message", s.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }


        map.put("url", "/sell/seller/category/list");
        map.put("message", "类目信息修改成功");

        return new ModelAndView("common/success", map);

    }
}
