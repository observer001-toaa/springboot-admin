package com.wbt.controller;

import cn.hutool.core.collection.CollUtil;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.wbt.common.ResponseResult;
import com.wbt.pojo.User;
import com.wbt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 15236
 */
@Api(tags="用户信息管理接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //添加或者更新

    @Operation(summary = "添加或者修改用户")
    @PostMapping
    public ResponseResult save(
            @Parameter(description = "用户实体") @RequestBody User user
    ) {
        return ResponseResult.success(userService.saveOrUpdate(user));
    }

    //删除

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseResult delete(
            @Parameter(description = "用户id") @PathVariable Integer id

    ) {
        return ResponseResult.success(userService.removeById(id));

    }

    //删除
    //TODO  这里把前端传的数据格式解决，把PostMapping改为标准的DeleMapping

    @Operation(summary = "批量删除用户")
    @PostMapping("/del/batch")
    public ResponseResult deleteBatch(
            @Parameter(description = "用户id") @RequestBody List<Integer> ids

    ) {
        return  ResponseResult.success(userService.removeBatchByIds(ids));

    }
    //查询所有数据

    @Operation(summary = "查询所有数据")
    @GetMapping
    public ResponseResult findAll() {
        List<User> users = userService.list();
        return ResponseResult.success(users);
    }
    @Operation(summary = "根据用户名查询数据")
    @GetMapping("/username/{username}")
    public ResponseResult findOne(
             @Parameter(description = "用户名") @PathVariable String username
    ) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return ResponseResult.success(userService.getOne(queryWrapper));
    }

    //分页查询

    @Operation(summary = "分页查询，并且可以按照用户名进行模糊搜索")
    @GetMapping("/page")
    public ResponseResult page(
            @Parameter(description = "页数") @RequestParam Integer pageNum,
            @Parameter(description = "页大小")@RequestParam Integer pageSize,
            @Parameter(description = "通过用户名模糊查询")@RequestParam(defaultValue = "") String username,
            @Parameter(description = "通过邮箱模糊查询")@RequestParam(defaultValue = "") String email,
            @Parameter(description = "通过地址模糊查询")@RequestParam(defaultValue = "") String address
    ) {

        Map<String, Object> res = new HashMap<>();
        IPage<User> page = userService.findPage(pageNum, pageSize, username, email, address);
        long total = page.getTotal();
        res.put("total", total);
        List<User> userList = page.getRecords();
        res.put("data", userList);
        return ResponseResult.success(res);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        //writer.setOnlyAlias(true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    //TODO 把excel表格的导入导出忽略一些实体类的字段
    @PostMapping("/import")
    public ResponseResult imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
         List<User> list = reader.readAll(User.class);

        return ResponseResult.success(userService.saveBatch(list));
    }
}
