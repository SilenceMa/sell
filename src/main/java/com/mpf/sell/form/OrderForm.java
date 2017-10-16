package com.mpf.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;


@Data
public class OrderForm {
    /*顾客姓名*/
    @NotEmpty(message = "姓名必填")
    private String name;
    /*手机号码*/
    @NotEmpty(message = "手机号码不能为空")
    private String phone;
    /*收货地址*/
    @NotEmpty(message = "收货地址不能为空")
    private String address;

    /*顾客的openid*/
    @NotEmpty(message = "顾客的openid不能为空")
    private String openid;

    /*购物车信息*/
    @NotEmpty(message = "购物车信息不能为空")
    private String items;
}
