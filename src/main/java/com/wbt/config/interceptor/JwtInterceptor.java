package com.wbt.config.interceptor;

import cn.hutool.core.util.StrUtil;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wbt.exception.ServiceException;
import com.wbt.pojo.User;
import com.wbt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 15236
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), "无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), "token验证失败，请重新登录");
        }
        // 根据token中的userid查询数据库
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), "用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            // 验证token
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), "token验证失败，请重新登录");
        }
        return true;
    }
}