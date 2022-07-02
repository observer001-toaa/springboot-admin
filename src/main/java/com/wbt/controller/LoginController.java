package com.wbt.controller;


import cn.hutool.core.util.StrUtil;
import com.wbt.common.ResponseResult;
import com.wbt.controller.dto.UserDto;
import com.wbt.pojo.User;
import com.wbt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author 15236
 */
@Api(tags="登陆管理接口")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    @Operation(summary = "登陆api")
    @PostMapping("/login")
    public ResponseResult login(
            @Parameter(description = "登陆请求参数") @RequestBody UserDto userDto
    ){
        String username=userDto.getUsername();
        String password=userDto.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return ResponseResult.error(HttpStatus.BAD_REQUEST.value(),"请求参数错误");
        }
        UserDto dto = userService.login(userDto);

        return ResponseResult.success(dto);
    }
    @Operation(summary = "注册")
    @PostMapping("/register")
    public ResponseResult register(
           @Parameter(description = "注册请求参数") @RequestBody UserDto userDto
    ){
        String username=userDto.getUsername();
        String password=userDto.getPassword();
        String nickname=userDto.getNickname();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)||StrUtil.isBlank(nickname)){
            return ResponseResult.error(HttpStatus.BAD_REQUEST.value(),"请求参数错误");
        }

        User user = userService.register(userDto);
        return  ResponseResult.success(user);
    }
}
