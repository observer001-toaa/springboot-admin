package com.wbt.service;

import com.wbt.pojo.Files;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 15236
* @description 针对表【sys_file】的数据库操作Service
* @createDate 2022-07-02 15:05:35
*/
public interface FileService extends IService<Files> {
    /**
     * getFileByMd5
     *
     * @param md5
     * @return com.wbt.pojo.Files
     * @author wbt
     * @create 2022/7/2
     **/
    Files getFileByMd5(String md5);
}
