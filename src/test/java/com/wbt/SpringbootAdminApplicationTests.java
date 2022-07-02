package com.wbt;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Date;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.wbt.mapper.UserMapper;
import com.wbt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAdminApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("李四");
        user.setPassword("123456");
        user.setNickname("ccc");
        user.setEmail("lisi@qq.com");
        user.setPhone("12345678912");
        user.setAddress("安徽合肥");
        userMapper.insert(user);
        BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
        BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
    }

}
