package com.wbt.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * @author wbt
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
@ApiModel("用户模型")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 主键
     */

    @TableId(type = IdType.AUTO)
    //@Alias("id")
    private Long id;

    /**
     * 用户名
     * @Alias对属性起别名，后面再UserController导入和导出的时候就不用了七别名了
     * import cn.hutool.core.annotation.Alias;
     */
    //@Alias("用户名")
    private String username;


    /**
     * 密码
     * @JsonIgnore返回json对象时忽略掉该注解标识的属性
     */
    @JsonIgnore
   // @Alias("密码")
    private String password;

    /**
     * 昵称
     */
    //@Alias("昵称")
    private String nickname;

    /**
     * 邮箱
     */
    //@Alias("邮箱")
    private String email;

    /**
     * 电话
     */
   // @Alias("电话")
    private String phone;

    /**
     * 地址
     */
   // @Alias("地址")
    private String address;

    private String avatar;
    /**
     * 逻辑删除
     */
    @TableLogic
    @JsonIgnore
    private Integer isDeleted;

    /**
     * 插入时间
     */
    @JsonIgnore
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}