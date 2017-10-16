package com.mpf.sell.controller;

import com.mpf.sell.config.ProjectUrlConfig;
import com.mpf.sell.constant.CookieConstant;
import com.mpf.sell.constant.RedisConstant;
import com.mpf.sell.dataobject.SellerInfo;
import com.mpf.sell.enums.ResultEnum;
import com.mpf.sell.service.SellerInfoService;
import com.mpf.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/seller/user")
@Slf4j
public class SellUserController {

    @Autowired
    private SellerInfoService service;

    /*引入redis*/
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @RequestMapping(value = "/login")
    public ModelAndView login(@RequestParam(value = "openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        //1.openid和数据库匹配
        SellerInfo seller = service.findSellerByOpenid(openid);
        if (seller == null) {
            map.put("message", ResultEnum.LOGIN_ERROR.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        //2.设置token至redis
        //2.1 设置token
        String token = UUID.randomUUID().toString();
        //2.2 设置过期时间
        Integer expire = RedisConstant.EXPIRE;
        log.info("token_ = {}", String.format(RedisConstant.TOKEN_PREFIX, token));
        log.info("token = {}", token);
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/order/list");

    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1. 从cookie 中查询

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 删除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            //3.删除cookie中的token
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("message",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
