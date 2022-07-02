package com.wbt.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbt.common.ResponseResult;
import com.wbt.pojo.Files;
import com.wbt.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 15236
 * 文件上传
 */
@Api(tags="文件上传下载管理接口")
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    FileService fileService;
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //存储到磁盘
        //定义一个文件唯一的标识码
        String fileUUID=IdUtil.fastSimpleUUID()+StrUtil.DOT+type;
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        //查询文件的MD5的文件是否存在，通过对比MD5避免上传相同的文件
        Files files = fileService.getFileByMd5(md5);

        if (files!=null){
            url= files.getUrl();
        }else {
            //把获取到的文件存储到磁盘目录上去
            file.transferTo(uploadFile);
            url="http://localhost:8081/file/"+fileUUID;
        }
        //存储数据库
        Files saveFiles = new Files();
        saveFiles.setName(originalFilename);
        saveFiles.setType(type);
        saveFiles.setSize(size/1024);
        saveFiles.setUrl(url);
        saveFiles.setMd5(md5);
        fileService.save(saveFiles);
        return url;
    }


    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Files files) {
        fileService.save(files);
        return ResponseResult.success();
    }

    //TODO 删除的时候把磁盘的文件也删除

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        return ResponseResult.success(fileService.removeById(id));
    }

    @PostMapping("/del/batch")
    public ResponseResult deleteBatch(@RequestBody List<Integer> ids) {

        return ResponseResult.success(ResponseResult.success(fileService.removeBatchByIds(ids)));
    }


    @GetMapping("/page")
    public ResponseResult findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String name) {
        Page<Files> page = new Page<>(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        Page<Files> filesPage = fileService.page(page, queryWrapper);
        res.put("data",filesPage.getRecords());
        res.put("total",filesPage.getTotal());
        return ResponseResult.success(res);
    }

}
