package com.wbt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbt.common.ResponseResult;
import com.wbt.controller.dto.UserDto;
import com.wbt.exception.ServiceException;
import com.wbt.service.UserService;
import com.wbt.pojo.User;
import com.wbt.mapper.UserMapper;
import com.wbt.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
* @author 15236
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-06-27 23:21:53
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
        private static final Log LOG=Log.get();
        //分页查询

        @Override
        public IPage<User> findPage(Integer pageNum, Integer pageSize, String username, String email, String address) {
                Page<User> userPage = new Page<>(pageNum, pageSize);
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                //userQueryWrapper.like("username",username).and(w->w.like("email",email));
                //不加and也可以

                if(!StringUtils.isEmpty(username)){
                        userQueryWrapper.like("username",username);
                }

                if(!StringUtils.isEmpty(email)){
                        userQueryWrapper.like("email",email);
                }

                if(!StringUtils.isEmpty(address)){
                        userQueryWrapper.like("address",address);
                }
                userQueryWrapper.orderByDesc("id");
                return page(userPage,userQueryWrapper);
        }

        @Override
        public UserDto login(UserDto userDto) {
                User userInfo = getUserInfo(userDto);
                //UserDto的属性和User实体中的部分属性相同，可以用工具类进行复制
                if (userInfo!=null){
                        BeanUtil.copyProperties(userInfo,userDto,true);
                        //设置token
                        String token = TokenUtils.getToken(userInfo.getId().toString(), userInfo.getPassword());
                        userDto.setToken(token);
                        return userDto;
                }else {
                        throw new ServiceException(HttpStatus.NO_CONTENT.value(),"用户名或者密码错误");
                }
        }

        @Override
        public User register(UserDto userDto) {
                User userInfo = getUserInfo(userDto);
                if (userInfo==null){
                        userInfo=new User();
                        BeanUtil.copyProperties(userDto,userInfo,true);
                        save(userInfo);
                        return userInfo;
                }else{
                        throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(),"用户名已经存在");
                }
        }
        private User getUserInfo(UserDto userDto){

                QueryWrapper<User> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("username",userDto.getUsername());
                queryWrapper.eq("password",userDto.getPassword());

                User one = null;
                try {
                        one = getOne(queryWrapper);

                } catch (Exception e) {
                        LOG.error(e);
                        throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(),"系统错误");
                }
                return one;
        }

}




