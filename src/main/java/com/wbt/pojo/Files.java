package com.wbt.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 15236
 * @TableName sys_file
 */
@TableName(value ="sys_file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 下载链接
     */
    private String url;
    /**
     *md5
     **/
    private String md5;
    /**
     * 逻辑删除
     */
    @TableLogic
    @JsonIgnore
    private Integer isDelete;

    /**
     * 是否禁用
     */
    private Integer enable;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}