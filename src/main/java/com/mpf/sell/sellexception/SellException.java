package com.mpf.sell.sellexception;

import com.mpf.sell.enums.ResultEnum;
import lombok.Data;

/**
 * spring 对RuntimeException进行实物回滚 对其他的exception不进行事物回滚
 */
@Data
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(ResultEnum resultEnum,String message){
        super(message);
        this.code = resultEnum.getCode();
    }
}
