package com.mpf.sell.utils;

import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.vo.ResultVo;

public class ResultUtils {
    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setMessage("请求成功");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo error(Integer code,String message){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage(message);
        return resultVo;
    }

    public static ResultVo error(ResultEnum resultEnum){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMessage(resultEnum.getMessage());
        return resultVo;
    }
}
