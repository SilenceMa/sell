package com.mpf.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PARAMS_ERROR(203,"参数错误"),
    PRODUCT_NOT_EXIT(204, "商品不存在"),
    PRODUCT_NOT_ENOUGH(205, "库存不够"),
    UN_KNOW_ERROR(500,"未知错误"),
    ORDER_MASTER_NOT_EXIT(206,"订单不存在"),
    ORDER_DETAIL_MASTER_NOT_EXIT(207,"订单详情不存在"),
    ORDER_STATUS_ERROR(208,"订单状态不正确"),
    ORDER_UPDATE_ERROR(209,"订单更新失败"),
    ORDER_DETAIL_EMPTY(210,"订单中无商品"),
    ORDER_PAY_STATUS_ERROR(211,"订单支付状态有误"),
    CART_EMPTY(212,"购物车不能为空"),
    OPEN_ID_EMPTY(213,"openid不能为空"),
    ORDER_ID_EMPTY(214,"orderId不能为空" ),
    ORDER_OWNER_ERROR(215,"该订单不属于当前用户" ),
    SUCCESS_CANCEL(216,"订单取消成功"),
    SUCCESS_FINISH(217,"订单完结成功"),
    PRODUCT_STATUS_ERROR(218,"商品状态不正确"),
    LOGIN_ERROR(219,"登录失败,登录信息不正确"),
    LOGOUT_SUCCESS(220,"登出成功"),
    REDIS_LOCK_ERROR(221,"哎呦喂，人也太多了吧，换个姿势试试"),
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultEnum() {
    }
}
