package com.mpf.sell.dataobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mpf.sell.utils.serialize.Date2LongSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class SellerInfo {
    @Id
    private String id;

    //用户名
    private String username;

    //密码
    private String password;

    //微信openid
    private String openid;

    //创建时间
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date create_time;

    //修改时间
    @JsonSerialize(using = Date2LongSerialize.class)
    private Date update_time;
}
