package com.ocean.platformspringboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ocean.platformspringboot.entity.User;
import com.ocean.platformspringboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
public class TokenUtils {
    private static IUserService staticUserService;
    @Resource
    private IUserService userService;
    @PostConstruct
    public void setStaticUserService(){
        staticUserService = userService;
    }

    public static String genToken(String id,String sign){
        return JWT.create().withAudience(id)
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))
                .sign(Algorithm.HMAC256(sign));
    }

    public static User getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String userID = JWT.decode(token).getAudience().get(0);
                return staticUserService.getUserById(Integer.valueOf(userID));
            }
        }catch (Exception e){
            log.error("解析token，失败",e);
            return null;
        }
        return null;
    }

}
