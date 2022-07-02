package com.wbt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbt.pojo.Files;
import com.wbt.service.FileService;
import com.wbt.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15236
* @description 针对表【sys_file】的数据库操作Service实现
* @createDate 2022-07-02 15:05:35
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files>
    implements FileService{
    public Files getFileByMd5(String md5){
        //查询文件的MD5的文件是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> list = list(queryWrapper);
        return list.size()==0?null:list.get(0);
    }
}




