package com.wbt.controller.dto;

import lombok.Data;

/**
 * @author 15236
 * 接收前端登陆请求的参数
 */
@Data
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String token;
}
