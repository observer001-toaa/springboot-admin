package com.wbt.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;


/**
 * @author 15236
 */
public class TokenUtils {

    public  static String getToken(String userId,String password){

        return JWT.create()
                //将userid 保存到token里面作为载荷
                .withAudience(userId)
                //2小时候token过期
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))
                //以password作为token的密匙
                .sign(Algorithm.HMAC256(password));
    }

}
