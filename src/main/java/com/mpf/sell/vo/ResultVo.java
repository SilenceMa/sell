package com.mpf.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * http请求的最外层对象
 */
@Data
public class ResultVo<T> implements Serializable{


    private static final long serialVersionUID = -8333443109055293614L;

    /*错误码*/
    private Integer code;
    /*错误信息*/
    private String message;
    /*json数据*/
    private T data;
}
