package com.mpf.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mpf.sell.dataobject.OrderDetail;
import com.mpf.sell.dataobject.OrderMaster;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

/**
 * 订单的扩展类
 * JsonInclude(JsonInclude.Include.NON_NULL) 空对象不返回
 */
@Data
@DynamicUpdate
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto extends OrderMaster {

    /*订单明细*/
    List<OrderDetail> orderDetails;
}
