package com.mpf.sell.handle;

import com.mpf.sell.config.ProjectUrlConfig;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.sellexception.ResponseBankException;
import com.mpf.sell.sellexception.SellAuthorizeException;
import com.mpf.sell.sellexception.SellException;
import com.mpf.sell.utils.ResultUtils;
import com.mpf.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVo sellException(Exception e) {
        if (e instanceof SellException) {
            SellException sellException = (SellException) e;
            return ResultUtils.error(sellException.getCode(),e.getMessage());
        }else {
            log.info("系统异常={}",e);
            return ResultUtils.error(ResultEnum.UN_KNOW_ERROR);
        }
    }
    //拦截登录异常
    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView sellerAuthorizeException(){
        return new ModelAndView("redirect:".concat(projectUrlConfig.getSell()).concat("/user/login"));
    }

    /**
     * 更改 http请求response的状态码
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void setResponseBankException(){

    }
}
