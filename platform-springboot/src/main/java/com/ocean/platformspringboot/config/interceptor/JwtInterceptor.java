package com.ocean.platformspringboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ocean.platformspringboot.common.Constants;
import com.ocean.platformspringboot.entity.User;
import com.ocean.platformspringboot.exception.ServiceException;
import com.ocean.platformspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = request.getHeader("token");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"没有token，请重新登陆");
        }
        String userID;
        try {
            userID = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
        User user = userService.getUserById(Integer.parseInt(userID));
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
        JWTVerifier jwtVerifier =JWT.require(Algorithm.HMAC256(user.getPwd())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
        return true;
    }
}
