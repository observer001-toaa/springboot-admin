package com.wbt.mapper;

import com.wbt.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 15236
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-06-27 23:21:53
* @Entity com.wbt.pojo.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

}




