package com.mpf.sell.enums;

import lombok.Getter;

@Getter
public enum  PayStatusEnum implements CodeEnum{
    WAIT(0,"等待支付"),
    FINISH(1,"支付成功")
    ;
    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
