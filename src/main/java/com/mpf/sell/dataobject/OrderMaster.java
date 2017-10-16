package com.mpf.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mpf.sell.enums.OrderStatusEnum;
import com.mpf.sell.enums.PayStatusEnum;
import com.mpf.sell.utils.EnumUtils;
import com.mpf.sell.utils.serialize.Date2LongSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    /*订单id*/
    @Id
    private String orderId;
    /*顾客姓名*/
    private String customerName;
    /*顾客电话*/
    private String customerPhone;
    /*顾客地址*/
    private String customerAddress;
    /*买家微信id*/
    private String customerOpenid;
    /*订单总金额*/
    private BigDecimal orderAmount;
    /*订单状态,默认0新订单*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /*支付状态，默认0未支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /*创建时间*/
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date createTime;
    /*更新时间*/
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date updateTime;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtils.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtils.getByCode(payStatus,PayStatusEnum.class);
    }

}
