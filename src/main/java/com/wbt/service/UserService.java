package com.wbt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wbt.common.ResponseResult;
import com.wbt.controller.dto.UserDto;
import com.wbt.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @author 15236
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-06-27 23:21:53
*/
public interface UserService extends IService<User> {

       /**
        * findPage
        *
        * @param pageNum
        * @param pageSize
        * @param username
        * @param email
        * @param address
        * @return com.baomidou.mybatisplus.core.metadata.IPage<com.wbt.pojo.User>
        * @author wbt
        * @create 2022/6/30
        **/
       IPage<User> findPage(Integer pageNum, Integer pageSize,  String username,  String email, String address);

      UserDto login(UserDto userDto);

     User register(UserDto userDto);
}
